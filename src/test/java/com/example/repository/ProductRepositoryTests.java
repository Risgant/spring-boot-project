package com.example.repository;

import com.example.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql("classpath:test-data/insert-products.sql")
class ProductRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("dateCreate").descending());
        Page<Product> products = productRepository.findAll(pageable);
        products.getContent();

        entityManager.persist(new Product());
        entityManager.flush();

        List<Product> productList = productRepository.findAll();
        assertEquals(0, productList.size());
    }
}
