package com.example.PracticalTasksAboutSpringDataJpa.service;

import com.example.PracticalTasksAboutSpringDataJpa.entity.Customer;
import com.example.PracticalTasksAboutSpringDataJpa.repository.AuditLogRepository;
import com.example.PracticalTasksAboutSpringDataJpa.repository.MailLogRepository;
import com.example.PracticalTasksAboutSpringDataJpa.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.transaction.TestTransaction;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import({
        OrderService.class,
        AuditService.class,
        EmailSenderService.class
})
class OrderServiceFirstVersionTest {
    @Autowired
    private OrderService service;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    private MailLogRepository mailLogRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    void firstVersion_afterExceptionRollbackAllEntities() {
        //given
        Customer customer = new Customer();
        customer.setEmail("emailWithoutAt");
        em.persist(customer);
        em.flush();
        em.clear();

        String givenDescription = "Please clean up my apartment";
        LocalDateTime givenExecutionTime = LocalDateTime.now().plusMonths(1);

        //when
        assertThrows(IllegalArgumentException.class,
                () -> service.createOrderFirstVersion(customer.getId(), givenDescription, givenExecutionTime));

        TestTransaction.end();
        TestTransaction.start();

        //then
        assertTrue(orderRepository.findAll().isEmpty());
        assertTrue(auditLogRepository.findAll().isEmpty());
        assertTrue(mailLogRepository.findAll().isEmpty());
    }
}
