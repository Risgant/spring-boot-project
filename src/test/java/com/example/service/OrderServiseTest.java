package com.example.service;

import com.example.repository.OrderRepository;
import com.example.serviсe.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
class OrderServiseTest {

    @MockBean
    private OrderService orderService;

    @Test
    public void testFindFirstPageOrders(){
        Assertions.assertEquals(orderService.findPage(0).size(), 5);
    }
}
