package com.example.controller;

import com.example.dto.OrderDto;
import com.example.dto.ProductDto;
import com.example.model.Order;
import com.example.model.Product;
import com.example.serviсe.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/get_all")
    public List<OrderDto> read(){
        return orderService.findAll().stream().
                map(p-> Pair.of(p, modelMapper.map(p, OrderDto.class))).
                map(p-> {
                    p.getSecond().setProductId(p.getFirst().
                            getProducts().stream().map(Product::getId).
                            collect(Collectors.toList()));
                    return p.getSecond(); }).
                collect(Collectors.toList());
    }

    @GetMapping("/get_all_sorted")
    public List<OrderDto> readSorted(){
        List<OrderDto> list = read();
        list.sort(Comparator.comparing(OrderDto::getDate));
        return list;
    }

    @PostMapping("/create")
    public void create(@RequestBody OrderDto orderDto){
        Order order = modelMapper.map(orderDto, Order.class);
        order.setProducts(orderDto.getProductId().stream().map(Product::new).collect(Collectors.toList()));
        orderService.create(order);
    }

    @PutMapping("/update")
    public void update(@RequestBody OrderDto orderDto){
        Order order = modelMapper.map(orderDto, Order.class);
        if(orderDto.getProductId() == null)
            orderDto.setProductId(new ArrayList<>());
        order.setProducts(orderDto.getProductId().stream().map(Product::new).collect(Collectors.toList()));
        orderService.update(order);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody OrderDto orderDto){
        orderService.delete(modelMapper.map(orderDto, Order.class));
    }
}
