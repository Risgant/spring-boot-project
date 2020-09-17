package com.example.serviсe.impl;

import com.example.exception.NoSuchObjectException;
import com.example.exception.ObjectAlreadyExistException;
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
    public void create(Product product) {
        if(productRepository.findById(product.getId()).isPresent())
            throw new ObjectAlreadyExistException();
        productRepository.save(product);
    }

    @Override
    public void update(Product product) {
        Product newProduct = productRepository.findById(product.getId()).
                orElseThrow(NoSuchObjectException::new);
        newProduct.setOrder(product.getOrder());
        newProduct.setPrice(product.getPrice());
        newProduct.setSeller(product.getSeller());
        newProduct.setTitle(product.getTitle());
        productRepository.save(newProduct);
    }

    @Override
    public void delete(Product product) {
        if(!productRepository.findById(product.getId()).isPresent())
            throw new NoSuchObjectException();
        productRepository.delete(product);
    }

//    @Override
//    public Product findById(int id) {
//        log.info("Fetching product with id {}", id);
//        return productRepository.findById(id).orElseThrow(NoSuchEntityException::new);
//    }
}
