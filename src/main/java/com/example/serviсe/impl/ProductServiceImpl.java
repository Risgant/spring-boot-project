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
    public Product create(Product product) {
        if(product.getOrder() != null && product.getOrder().getId() != null) {
            Order order = orderRepository.findById(product.getOrder().getId()).orElseThrow(NoSuchObjectException::new);
            order.setBynAmount(order.getBynAmount().add(product.getPrice()));
            orderRepository.save(order);
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        Product newProduct = productRepository.findById(product.getId()).
                orElseThrow(NoSuchObjectException::new);
        if(newProduct.getOrder() != null){
            Order order = newProduct.getOrder();
            order.setBynAmount(order.getBynAmount().subtract(newProduct.getPrice()));
            order.getProducts().remove(newProduct);
            if(order.getProducts().size() == 0)
                orderRepository.delete(order);
            else
                orderRepository.save(order);
        }

        if(product.getOrder() != null) {
            Order order = orderRepository.findById(product.getOrder().getId()).orElseThrow(NoSuchObjectException::new);
            if(product.getPrice() != null)
                order.setBynAmount(order.getBynAmount().add(product.getPrice()));
            orderRepository.save(order);
        }
        return productRepository.save(product);
    }

    @Override
    public void delete(int id) {
        Product deletedProduct = productRepository.findById(id).orElseThrow(NoSuchObjectException::new);
        productRepository.delete(deletedProduct);
    }
}
