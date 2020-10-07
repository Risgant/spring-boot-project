package com.example.service;

import com.example.model.Order;
import com.example.model.Product;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.serviсe.ProductService;
import com.example.serviсe.impl.ProductServiceImpl;
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
import java.util.Optional;

import static org.mockito.Mockito.when;

@DataJpaTest
public class ProductServiceTest {

    @TestConfiguration
    static class testConfiguration{
        @Bean
        public ProductService productService(){
            return new ProductServiceImpl();
        }
    }

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    OrderRepository orderRepository;

    @Test
    public void testFindFirstPage() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Product product = createProduct(1, 2.0);
        Page page = new PageImpl(List.of(product, product, product, product, product));
        when(productRepository.findAll(pageRequest)).thenReturn(page);
        Assertions.assertEquals(5, productService.findPage(0).size());
    }

    @Test
    public void testCreate(){
        Product product = createProduct(1, 2.0);
        when(productRepository.save(product)).thenReturn(product);
        Product product1 = productService.create(product);
        Assertions.assertEquals(1, product.getId());
        Assertions.assertEquals(BigDecimal.valueOf(2.0), product.getPrice());
    }

    @Test
    public void testUpdate(){
        Product product = createProduct(1, 2.0);
        Product oldProduct = createProduct(1, 3.0);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(oldProduct));
        when(productRepository.save(product)).thenReturn(product);
        Assertions.assertEquals(1, product.getId());
        Assertions.assertEquals(BigDecimal.valueOf(2.0), product.getPrice());
    }


    private Product createProduct(int id, double price){
        Product product = new Product();
        product.setId(id);
        product.setPrice(BigDecimal.valueOf(price));
        return product;
    }
}
