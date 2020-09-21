package com.example.serviсe.impl;

import com.example.exception.NoSuchObjectException;
import com.example.exception.ObjectAlreadyExistException;
import com.example.exception.ObjectAlreadyUsedException;
import com.example.model.Order;
import com.example.model.Product;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.serviсe.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Order> findAll() {
        log.info("fetching all orders");
        return orderRepository.findAll();
    }

    @Override
    public void create(Order order) {
        if(orderRepository.findById(order.getId()).isPresent())
            throw new ObjectAlreadyExistException();
        List<Product> products = productRepository.findAllById(order.getProducts().stream().
                map(Product::getId).
                collect(Collectors.toList()));
        if(products.stream().anyMatch(p->p.getOrder()!=null))
            throw new ObjectAlreadyUsedException();
        BigDecimal num = products.stream().map(Product::getPrice).
                reduce(BigDecimal::add).get();
        order.setBynAmount(products.stream().map(Product::getPrice).
                reduce(BigDecimal::add).get());
        orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        List<Product> newProducts = productRepository.findAllById(order.getProducts().stream().
                map(Product::getId).
                collect(Collectors.toList()));
        Order newOrder = orderRepository.findById(order.getId()).
                orElseThrow(NoSuchObjectException::new);
        newOrder.setCustomer(order.getCustomer());
        List<Product> oldProducts = newOrder.getProducts();
        newOrder.setDate(order.getDate());
        oldProducts.forEach(p->p.setOrder(null));
        newProducts.forEach(p->p.setOrder(newOrder));
        newOrder.setProducts(newProducts);
        newOrder.setBynAmount(newProducts.stream().map(Product::getPrice).
                reduce(BigDecimal::add).
                orElseThrow(NoSuchObjectException::new));
        productRepository.saveAll(oldProducts);
        productRepository.saveAll(newProducts);
        orderRepository.save(newOrder);
    }

    @Override
    public void delete(Order order) {
        if(!orderRepository.findById(order.getId()).isPresent())
            throw new NoSuchObjectException();
        orderRepository.delete(order);
    }
}
