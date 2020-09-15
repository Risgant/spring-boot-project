package com.example.serviсe.impl;

import com.example.exception.NoSuchEntityException;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.serviсe.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }

    @Override
    public Product findById(int id) {
        log.info("Fetching product with id {}", id);
        return productRepository.findById(id).orElseThrow(NoSuchEntityException::new);
    }
}
