package com.example.serviсe;

import com.example.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> findPage(int page);

    Order create(Order order);

    Order update(Order order);

//    void delete(Order order);

    void delete(int orderId);
}
