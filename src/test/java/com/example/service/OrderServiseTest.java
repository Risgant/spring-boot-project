package com.example.service;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.serviсe.OrderService;
import com.example.serviсe.impl.OrderServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@SpringJUnitConfig
//@ExtendWith(SpringExtension.class)
@DataJpaTest
//@Sql({"classpath:database/h2CustomerData.sql", "classpath:database/h2SellerData.sql", "classpath:database/h2OrderData.sql", "classpath:database/h2ProductData.sql"})
class OrderServiseTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public OrderService orderService() {
            return new OrderServiceImpl();
        }
    }

    @Autowired
    private OrderService orderService;

    @MockBean
    OrderRepository orderRepository;
    @MockBean
    ProductRepository productRepository;

    @Test
    public void testFindFirstPageOrders() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Order order = new Order();
        Page page = new PageImpl(List.of(order, order, order, order, order));
        when(orderRepository.findAll(pageRequest)).thenReturn(page);

        Assertions.assertEquals(5, orderService.findPage(0).size());
    }
}
