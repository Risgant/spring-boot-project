package com.example.service;

import com.example.model.Order;
import com.example.model.Product;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.serviсe.OrderService;
import com.example.serviсe.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

//@SpringJUnitConfig
//@ExtendWith(SpringExtension.class)
@DataJpaTest
//@Sql({"classpath:database/h2CustomerData.sql", "classpath:database/h2SellerData.sql", "classpath:database/h2OrderData.sql", "classpath:database/h2ProductData.sql"})
class OrderServiseTest {

    @TestConfiguration
    static class testContextConfiguration {

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
    public void findAllTest() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Order order = createOrder(1, 1);
        Page page = new PageImpl(List.of(order, order, order, order, order));
        when(orderRepository.findAll(pageRequest)).thenReturn(page);

        Assertions.assertEquals(5, orderService.findAll(0, 5, false).size());
    }

    @Test
    public void createTest(){
        Order order = createOrder(1, 1);
        when(productRepository.findAllById(Collections.singletonList(1))).thenReturn(order.getProducts());
        when(orderRepository.save(order)).thenReturn(order);
        Order order1 = orderService.create(order);
        Assertions.assertEquals(1, order1.getId());
        Assertions.assertEquals(1, order1.getProducts().get(0).getId());
    }

    @Test
    public void updateTest(){
        Order order = createOrder(1, 1);
        Order oldOrder = createOrder(1, 2);
        when(productRepository.findAllById(Collections.singletonList(1))).thenReturn(order.getProducts());
        when(orderRepository.findById(order.getId())).thenReturn(java.util.Optional.of(oldOrder));
        when(orderRepository.save(order)).thenReturn(order);
        Order order1 = orderService.update(order);
        Assertions.assertEquals(1, order1.getId());
        Assertions.assertEquals(1, order1.getProducts().get(0).getId());
    }

    private Order createOrder(int orderId, int productId){
        Order order = new Order();
        order.setId(orderId);
        order.setDate(LocalDate.now());
        Product product = new Product();
        product.setId(productId);
        product.setPrice(BigDecimal.valueOf(2.0));
        List<Product> products = Collections.singletonList(product);
        order.setProducts(products);
        return order;
    }
}
