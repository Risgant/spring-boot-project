package com.example.controller;

import com.example.dto.OrderDto;
import com.example.dto.ProductDto;
import com.example.model.Order;
import com.example.serviсe.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

public class OrderController {
    @Autowired
    private OrderService OrderService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/get_all")
    public List<OrderDto> read(){
        return OrderService.findAll().stream()
                .map(o->modelMapper.map(o, OrderDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/get_all_sorted")
    public List<OrderDto> readSorted(){
        List<OrderDto> list = read();
        list.sort((a, b)-> a.getDate().compareTo(b.getDate()));
        return list;
    }

    @PostMapping("/create")
    public void create(@RequestBody OrderDto orderDto){
        OrderService.create(modelMapper.map(orderDto, Order.class));
    }

    @PutMapping("/update")
    public void update(@RequestBody OrderDto orderDto){
        OrderService.update(modelMapper.map(orderDto, Order.class));
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody OrderDto orderDto){
        OrderService.delete(modelMapper.map(orderDto, Order.class));
    }
}
