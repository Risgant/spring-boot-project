package com.example.serviсe.impl;

import com.example.exception.NoSuchObjectException;
import com.example.exception.ObjectAlreadyExistException;
import com.example.model.Order;
import com.example.model.Product;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.serviсe.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Product> findPage(int page) {
        log.info("Fetching all products");
        return productRepository.findAll(PageRequest.of(page, 5)).getContent();
    }

    @Override
    public void create(Product product) {
        if(product.getOrder() != null && product.getOrder().getId() != null) {
            Order order = orderRepository.findById(product.getOrder().getId()).orElseThrow(NoSuchObjectException::new);
            order.setBynAmount(order.getBynAmount().add(product.getPrice()));
            orderRepository.save(order);
        }
        productRepository.save(product);
    }

    @Override
    public void update(Product product) {
        Product newProduct = productRepository.findById(product.getId()).
                orElseThrow(NoSuchObjectException::new);
        if(newProduct.getOrder() != null){
            Order order = newProduct.getOrder();
            order.setBynAmount(order.getBynAmount().subtract(newProduct.getPrice()));
            orderRepository.save(order);
        }
        newProduct.setOrder(product.getOrder());
        newProduct.setPrice(product.getPrice());
        newProduct.setSeller(product.getSeller());
        newProduct.setTitle(product.getTitle());
        if(product.getOrder() != null) {
            Order order = orderRepository.findById(newProduct.getOrder().getId()).orElseThrow(NoSuchObjectException::new);
            order.setBynAmount(order.getBynAmount().add(newProduct.getPrice()));
            orderRepository.save(order);
        }
        productRepository.save(newProduct);
    }

    @Override
    public void delete(Product product) {
        Product deletedProduct = productRepository.findById(product.getId()).orElseThrow(NoSuchObjectException::new);
        productRepository.delete(deletedProduct);
        if(deletedProduct.getOrder() != null && !productRepository.findById(deletedProduct.getId()).isPresent()){
            Order order = orderRepository.findById(deletedProduct.getOrder().getId()).orElseThrow(NoSuchObjectException::new);
            order.setBynAmount(order.getBynAmount().subtract(deletedProduct.getPrice()));
            orderRepository.save(order);
        }
    }
}
