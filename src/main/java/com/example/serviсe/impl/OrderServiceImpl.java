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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Order> findPage(int page) {
        log.info("fetching all orders");
        return orderRepository.findAll(PageRequest.of(page, 5)).getContent();
    }

    @Override
    public Order create(Order order) {
        List<Product> products = productRepository.findAllById(order.getProducts().stream().
                map(Product::getId).
                collect(Collectors.toList()));
        if(products.stream().anyMatch(p->p.getOrder()!=null))
            throw new ObjectAlreadyUsedException();
        products.forEach(p->p.setOrder(order));
        order.setBynAmount(products.stream().map(Product::getPrice).
                reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0)));
        order.setProducts(products);
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        List<Product> newProducts = productRepository.findAllById(order.getProducts().stream().
                map(Product::getId).
                collect(Collectors.toList()));
        if(order.getProducts().size() != newProducts.size())
            throw new NoSuchObjectException();
        Order oldOrder = orderRepository.findById(order.getId()).
                orElseThrow(NoSuchObjectException::new);
        List<Product> oldProducts = oldOrder.getProducts();
        oldProducts.forEach(p->p.setOrder(null));
        newProducts.forEach(p->p.setOrder(order));
        order.setProducts(newProducts);
        order.setBynAmount(newProducts.stream().map(Product::getPrice).
                reduce(BigDecimal::add).
                orElseThrow(NoSuchObjectException::new));
        productRepository.saveAll(oldProducts);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void delete(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NoSuchObjectException::new);
        orderRepository.delete(order);
    }
}
