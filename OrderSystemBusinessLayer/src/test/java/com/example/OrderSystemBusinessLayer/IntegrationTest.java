package com.example.OrderSystemBusinessLayer;

import com.example.OrderSystemBusinessLayer.client.PaymentGateway;
import com.example.OrderSystemBusinessLayer.dto.OrderItemRequest;
import com.example.OrderSystemBusinessLayer.dto.OrderRequest;
import com.example.OrderSystemBusinessLayer.dto.OrderResponse;
import com.example.OrderSystemBusinessLayer.entity.*;
import com.example.OrderSystemBusinessLayer.model.OrderStatus;
import com.example.OrderSystemBusinessLayer.repository.*;
import jakarta.persistence.EntityManager;
import org.assertj.core.data.Offset;
import org.hibernate.AssertionFailure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Sql(
        scripts = "/sql/truncate_all.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
class IntegrationTest extends IntegrationTestBase {
    @Autowired
    private SampleDataBuilder sampleDataBuilder;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DiscountCodeRepository discountCodeRepository;
    @Autowired
    private OrderRepository orderRepository;
    @MockitoBean
    private PaymentGateway paymentGateway;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @BeforeEach
    void setUp() {
        sampleDataBuilder.initData();
    }

    @Test
    void givenInvalidCustomerIdWhenCreateOrderThenReturnBadRequest() {
        //given
        Long givenCustomerId = Long.MAX_VALUE;

        OrderRequest orderRequest = new OrderRequest(
                null,
                null,
                givenCustomerId
        );

        //when
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                "/order/create",
                HttpMethod.POST,
                new HttpEntity<>(orderRequest),
                new ParameterizedTypeReference<>() {
                }
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertThat(response.getBody().get("message"))
                .isEqualTo("Customer with id: " + givenCustomerId + " not found");
        checkOrderAndOrderItem();
    }

    @Test
    void givenInvalidProductIdInOrderItemRequestWhenCreateOrderThenReturnBadRequest() {
        //given
        Long givenProductId = Long.MAX_VALUE;
        Customer givenCustomer = findCustomer();

        List<OrderItemRequest> orderItemRequests = List.of(new OrderItemRequest(givenProductId, 1));

        OrderRequest orderRequest = new OrderRequest(
                orderItemRequests,
                null,
                givenCustomer.getId()
        );

        //when
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                "/order/create",
                HttpMethod.POST,
                new HttpEntity<>(orderRequest),
                new ParameterizedTypeReference<>() {
                }
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertThat(response.getBody().get("message"))
                .isEqualTo("Product with id: " + givenProductId + " not found");
        checkOrderAndOrderItem();
    }

    @Test
    void givenMoreQuantityInOrderItemRequestThanStockQuantityWhenCreateOrderThenReturnBadRequest() {
        //given
        Product givenProduct = findProduct();
        Customer givenCustomer = findCustomer();
        Integer givenInvalidQuantity = Integer.MAX_VALUE;

        List<OrderItemRequest> orderItemRequests = List.of(
                new OrderItemRequest(givenProduct.getId(), givenInvalidQuantity));

        OrderRequest orderRequest = new OrderRequest(
                orderItemRequests,
                null,
                givenCustomer.getId()
        );

        //when
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                "/order/create",
                HttpMethod.POST,
                new HttpEntity<>(orderRequest),
                new ParameterizedTypeReference<>() {
                }
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertThat(response.getBody().get("message"))
                .isEqualTo("Quantity in order cannot be more than stock quantity");
        checkOrderAndOrderItem();
    }

    @Test
    void givenInvalidDiscountCodeWhenCreateOrderThenReturnBadRequest() {
        //given
        Product givenProduct = findProduct();
        Customer givenCustomer = findCustomer();
        String givenInvalidDiscountCode = UUID.randomUUID().toString();

        List<OrderItemRequest> orderItemRequests = List.of(
                new OrderItemRequest(
                        givenProduct.getId(),
                        givenProduct.getStockQuantity() - 1
                )
        );

        OrderRequest orderRequest = new OrderRequest(
                orderItemRequests,
                List.of(givenInvalidDiscountCode),
                givenCustomer.getId()
        );

        //when
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                "/order/create",
                HttpMethod.POST,
                new HttpEntity<>(orderRequest),
                new ParameterizedTypeReference<>() {
                }
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertThat(response.getBody().get("message"))
                .isEqualTo("Discount code: " + givenInvalidDiscountCode + " not found");
        checkOrderAndOrderItem();
    }

    @Test
    void givenExpiredDiscountCodeWhenCreateOrderThenReturnBadRequest() {
        //given
        Product givenProduct = findProduct();
        Customer givenCustomer = findCustomer();
        DiscountCode givenDiscountCode = findDiscountCode();
        givenDiscountCode.setValidUntil(LocalDate.now().minusDays(1));
        discountCodeRepository.saveAndFlush(givenDiscountCode);
        String code = givenDiscountCode.getCode();

        List<OrderItemRequest> orderItemRequests = List.of(
                new OrderItemRequest(
                        givenProduct.getId(),
                        givenProduct.getStockQuantity() - 1
                )
        );

        OrderRequest orderRequest = new OrderRequest(
                orderItemRequests,
                List.of(code),
                givenCustomer.getId()
        );

        //when
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                "/order/create",
                HttpMethod.POST,
                new HttpEntity<>(orderRequest),
                new ParameterizedTypeReference<>() {
                }
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertThat(response.getBody().get("message"))
                .isEqualTo("Discount code: " + code + " is expired");
        checkOrderAndOrderItem();
    }

    @Test
    void givenTotalValueLessThenMinimumToUseDiscountCodeWhenCreateOrderThenReturnBadRequest() {
        //given
        Product givenProduct = findProduct();
        Customer givenCustomer = findCustomer();
        DiscountCode givenDiscountCode = findDiscountCode();
        BigDecimal givenMinimumOrderValue = new BigDecimal("50000.00");
        givenDiscountCode.setMinimumOrderValue(givenMinimumOrderValue);
        discountCodeRepository.saveAndFlush(givenDiscountCode);
        String code = givenDiscountCode.getCode();

        List<OrderItemRequest> orderItemRequests = List.of(
                new OrderItemRequest(
                        givenProduct.getId(),
                        givenProduct.getStockQuantity() - 1
                )
        );

        OrderRequest orderRequest = new OrderRequest(
                orderItemRequests,
                List.of(code),
                givenCustomer.getId()
        );

        //when
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                "/order/create",
                HttpMethod.POST,
                new HttpEntity<>(orderRequest),
                new ParameterizedTypeReference<>() {
                }
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertThat(response.getBody().get("message"))
                .isEqualTo("Minimum Order Value is less than minimum (" +
                        givenMinimumOrderValue + ") to apply code");
        checkOrderAndOrderItem();
    }

    @Test
    void givenTotalValueMoreThan1000WhenCreateOrderThenReturnOrderWithFreeShip() {
        //given
        Product givenProduct = findProduct();
        Customer givenCustomer = findCustomer();
        givenProduct.setPrice(new BigDecimal(1001));
        productRepository.saveAndFlush(givenProduct);

        List<OrderItemRequest> orderItemRequests = List.of(
                new OrderItemRequest(
                        givenProduct.getId(),
                        givenProduct.getStockQuantity() - 1
                )
        );

        OrderRequest orderRequest = new OrderRequest(
                orderItemRequests,
                null,
                givenCustomer.getId()
        );

        when(paymentGateway.initPayment(anyString(), any(BigDecimal.class)))
                .thenReturn(200);

        //when
        ResponseEntity<OrderResponse> response = restTemplate.postForEntity(
                "/order/create",
                orderRequest,
                OrderResponse.class
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertNotNull(response.getBody());
        OrderResponse orderResponse = response.getBody();

        Order order = orderRepository.findById(orderResponse.orderId())
                .orElseThrow(() -> new AssertionFailure("Order not found"));

        assertThat(order.getShippingCost()).isCloseTo(BigDecimal.ZERO, Offset.strictOffset(BigDecimal.ONE));
    }

    @Test
    void givenTotalValueLessThan1000WhenCreateOrderThenReturnOrderWithNormalCostShip() {
        //given
        Product givenProduct = findProduct();
        Customer givenCustomer = findCustomer();
        givenProduct.setPrice(new BigDecimal(1));
        productRepository.saveAndFlush(givenProduct);

        List<OrderItemRequest> orderItemRequests = List.of(
                new OrderItemRequest(
                        givenProduct.getId(),
                        givenProduct.getStockQuantity() - 1
                )
        );

        OrderRequest orderRequest = new OrderRequest(
                orderItemRequests,
                null,
                givenCustomer.getId()
        );

        when(paymentGateway.initPayment(anyString(), any(BigDecimal.class)))
                .thenReturn(200);

        //when
        ResponseEntity<OrderResponse> response = restTemplate.postForEntity(
                "/order/create",
                orderRequest,
                OrderResponse.class
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertNotNull(response.getBody());
        OrderResponse orderResponse = response.getBody();

        Order order = orderRepository.findById(orderResponse.orderId())
                .orElseThrow(() -> new AssertionFailure("Order not found"));

        assertThat(order.getShippingCost()).isCloseTo(BigDecimal.valueOf(15), Offset.strictOffset(BigDecimal.ONE));
    }

    @Test
    void givenErrorFromExternalPaymentGatewayWhenCreateOrderThenReturnBadRequest() {
        //given
        Product givenProduct = findProduct();
        Customer givenCustomer = findCustomer();
        givenProduct.setPrice(new BigDecimal(1));
        productRepository.saveAndFlush(givenProduct);

        List<OrderItemRequest> orderItemRequests = List.of(
                new OrderItemRequest(
                        givenProduct.getId(),
                        givenProduct.getStockQuantity() - 1
                )
        );

        OrderRequest orderRequest = new OrderRequest(
                orderItemRequests,
                null,
                givenCustomer.getId()
        );

        int givenResponseCode = 500;
        when(paymentGateway.initPayment(anyString(), any(BigDecimal.class)))
                .thenReturn(500);

        //when
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                "/order/create",
                HttpMethod.POST,
                new HttpEntity<>(orderRequest),
                new ParameterizedTypeReference<>() {
                }
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertThat(response.getBody().get("message"))
                .isEqualTo("Payment failed with response code: " + givenResponseCode);
        checkOrderAndOrderItem();
    }

    @Test
    void givenValidDataWhenCreateOrderThenReturnOrderResponseAndSaveOrderInDatabase() {
        //given
        Product givenProduct = findProduct();
        Customer givenCustomer = findCustomer();
        DiscountCode givenDiscountCode = findDiscountCode();
        BigDecimal expectedTotal = calculateTotal(givenProduct, givenDiscountCode);
        givenDiscountCode.setMinimumOrderValue(BigDecimal.ONE);
        discountCodeRepository.saveAndFlush(givenDiscountCode);

        List<OrderItemRequest> orderItemRequests = List.of(
                new OrderItemRequest(
                        givenProduct.getId(),
                        givenProduct.getStockQuantity() - 1
                )
        );

        OrderRequest orderRequest = new OrderRequest(
                orderItemRequests,
                List.of(givenDiscountCode.getCode()),
                givenCustomer.getId()
        );

        when(paymentGateway.initPayment(anyString(), any(BigDecimal.class)))
                .thenReturn(200);

        //when
        ResponseEntity<OrderResponse> response = restTemplate.postForEntity(
                "/order/create",
                orderRequest,
                OrderResponse.class
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertNotNull(response.getBody());
        OrderResponse orderResponse = response.getBody();

        Order order = orderRepository.findById(orderResponse.orderId())
                .orElseThrow(() -> new AssertionFailure("Order not found"));

        assertThat(order.getTotalValue()).isCloseTo(expectedTotal, Offset.strictOffset(BigDecimal.ONE));
        assertThat(order.getCustomer()).isEqualTo(givenCustomer);

        Product product = order.getOrderItems()
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionFailure("OrderItem not found"))
                .getProduct();

        assertThat(product).isEqualTo(givenProduct);
        assertThat(product.getStockQuantity()).isEqualTo(1);
    }

    @Test
    void givenOrderWithStatusPendingWhenCancelOrderThenReturnOK() {
        //given
        Product givenProduct = findProduct();
        Customer givenCustomer = findCustomer();
        Integer givenQuantityToIncrease = 1;

        Order givenOrder = sampleDataBuilder.createOrder(
                OrderStatus.PENDING, givenProduct.getId(), givenCustomer.getId(), givenQuantityToIncrease
        );

        //when
        restTemplate.put(
                "/order/cancel/" + givenOrder.getId(),
                null
        );

        //then
        Order resultOrder = orderRepository.findById(givenOrder.getId())
                .orElseThrow(() -> new AssertionFailure("Order not found"));

        assertThat(resultOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);

        Product resultProduct = productRepository.findById(givenProduct.getId())
                .orElseThrow(() -> new AssertionFailure("Product not found"));

        assertThat(resultProduct.getStockQuantity()).isEqualTo(givenProduct.getStockQuantity() + 1);
    }

    @Test
    void givenInvalidOrderIdWhenCancelOrderThenReturnBadRequest() {
        //given
        Product givenProduct = findProduct();
        Customer givenCustomer = findCustomer();
        Integer givenQuantityToIncrease = 1;
        long givenOrderId = Long.MAX_VALUE;

        Order givenOrder = sampleDataBuilder.createOrder(
                OrderStatus.PENDING, givenProduct.getId(), givenCustomer.getId(), givenQuantityToIncrease
        );

        //when
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                "/order/cancel/" + givenOrderId,
                HttpMethod.PUT,
                new HttpEntity<>(null),
                new ParameterizedTypeReference<>() {
                }
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertThat(response.getBody().get("message"))
                .isEqualTo("Order with id: " + givenOrderId + " not found");

        Product resultProduct = productRepository.findById(givenProduct.getId())
                .orElseThrow(() -> new AssertionFailure("Product not found"));

        assertThat(resultProduct.getStockQuantity()).isEqualTo(givenProduct.getStockQuantity());
    }

    @Test
    void givenOrderStatusNotPendingWhenCancelOrderThenReturnBadRequest() {
        //given
        Product givenProduct = findProduct();
        Customer givenCustomer = findCustomer();
        Integer givenQuantityToIncrease = 1;

        Order givenOrder = sampleDataBuilder.createOrder(
                OrderStatus.DELIVERED, givenProduct.getId(), givenCustomer.getId(), givenQuantityToIncrease
        );

        //when
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                "/order/cancel/" + givenOrder.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(null),
                new ParameterizedTypeReference<>() {
                }
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertThat(response.getBody().get("message"))
                .isEqualTo("You cannot cancel order who have different status than pending");

        Product resultProduct = productRepository.findById(givenProduct.getId())
                .orElseThrow(() -> new AssertionFailure("Product not found"));

        assertThat(resultProduct.getStockQuantity()).isEqualTo(givenProduct.getStockQuantity());
    }

    private Customer findCustomer() {
        return customerRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionFailure("Customer not found"));
    }

    private Product findProduct() {
        return productRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionFailure("Product not found"));
    }

    private DiscountCode findDiscountCode() {
        return discountCodeRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new AssertionFailure("Discount code not found"));
    }

    private void checkOrderAndOrderItem() {
        assertThat(orderItemRepository.findAll().isEmpty()).isTrue();
        assertThat(orderRepository.findAll().isEmpty()).isTrue();
    }

    private BigDecimal calculateTotal(Product givenProduct, DiscountCode givenDiscountCode) {
        BigDecimal price = givenProduct.getPrice();
        int quantity = givenProduct.getStockQuantity() - 1;

        BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));
        BigDecimal percent = givenDiscountCode.getValue().divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN);
        BigDecimal valueToDecease = total.multiply(percent);
        total = total.subtract(valueToDecease);

        //ship
        if (total.compareTo(BigDecimal.valueOf(1000)) > 0)
            total = total.add(BigDecimal.valueOf(15));

        return total;
    }
}
