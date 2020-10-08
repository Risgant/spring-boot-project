package com.example.controller;

import com.example.dto.OrderDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<OrderDto> read(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int pageSize,
                               @RequestParam(defaultValue = "false") boolean sorted){
        return orderService.findAll(page, pageSize, sorted).stream().
                map(p-> Pair.of(p, modelMapper.map(p, OrderDto.class))).
                map(p-> {
                    p.getSecond().setProductId(p.getFirst().
                            getProducts().stream().map(Product::getId).
                            collect(Collectors.toList()));
                    return p.getSecond(); }).
                collect(Collectors.toList());
    }
//
//    @GetMapping("/get_page_sorted/{page}")
//    public List<OrderDto> readSorted(@PathVariable int page){
//        List<OrderDto> list = read(page);
//        list.sort(Comparator.comparing(OrderDto::getDate));
//        return list;
//    }

    @PostMapping
    public void create(@RequestBody OrderDto orderDto){
        Order order = modelMapper.map(orderDto, Order.class);
        order.setProducts(orderDto.getProductId().stream().map(id->{
            Product product = new Product();
            product.setId(id);
            return product;
        }).collect(Collectors.toList()));
        orderService.create(order);
    }

    @PutMapping
    public void update(@RequestBody OrderDto orderDto){
        Order order = modelMapper.map(orderDto, Order.class);
        if(orderDto.getProductId() == null)
            orderDto.setProductId(new ArrayList<>());
        order.setProducts(orderDto.getProductId().stream().map(id->{
            Product product = new Product();
            product.setId(id);
            return product;
        }).collect(Collectors.toList()));
        orderService.update(order);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        orderService.delete(id);
    }
}
