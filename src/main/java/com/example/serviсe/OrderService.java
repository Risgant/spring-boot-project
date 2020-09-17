package com.example.serviсe;

import com.example.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();

    void create(Order order);

    void update(Order order);

    void delete(Order order);
}
