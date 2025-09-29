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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import({
        OrderService.class,
        AuditService.class,
        EmailSenderService.class
})
class OrderServiceSecondVersionTest {
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
    void secondVersion_afterExceptionRollbackAllEntitiesWithoutEmail() {
        //given
        Customer customer = new Customer();
        customer.setEmail("valid@email");
        em.persist(customer);
        em.flush();
        em.clear();

        TestTransaction.flagForCommit();
        TestTransaction.end();

        String givenDescription = "Please clean up my apartment";
        LocalDateTime givenInvalidExecutionTime = LocalDateTime.now().minusMonths(1);

        //when
        assertThrows(IllegalArgumentException.class,
                () -> service.createOrderSecondVersion(customer.getId(), givenDescription, givenInvalidExecutionTime));

        //then
        assertTrue(orderRepository.findAll().isEmpty());
        assertTrue(auditLogRepository.findAll().isEmpty());
        assertThat(mailLogRepository.findAll()).hasSize(1);
    }
}
