package com.example.serviсe.impl;

import com.example.exception.NoSuchObjectException;
import com.example.exception.SuchObjectAlreadyExist;
import com.example.model.Order;
import com.example.model.Product;
import com.example.repository.OrderRepository;
import com.example.serviсe.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<Order> findAll() {
        log.info("fetching all orders");
        return orderRepository.findAll();
    }

    @Override
    public void create(Order order) {
        if(orderRepository.findById(order.getId()).isPresent())
            throw new SuchObjectAlreadyExist();
//        order.setPrice(order
//                .getProducts()
//                .stream()
//                .map(Product::getPrice)
//                .reduce(0.0, Double::sum)
//                .doubleValue());
        orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
//        Order newOrder = orderRepository.findById(order.getId()).
//                orElseThrow(NoSuchObjectException::new);
//        newOrder.setCustomer(order.getCustomer());
//        newOrder.setDate(order.getDate());
//        newOrder.setProducts(order.getProducts());
//
//        newOrder.setUsdAmount(order.getUsdAmount());
//        orderRepository.save(newOrder);
    }

    @Override
    public void delete(Order order) {
        if(!orderRepository.findById(order.getId()).isPresent())
            throw new NoSuchObjectException();
        orderRepository.delete(order);
    }
}
