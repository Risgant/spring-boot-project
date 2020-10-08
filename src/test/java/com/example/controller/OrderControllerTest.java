package com.example.controller;

import com.example.dto.OrderDto;
import com.example.exception.NoSuchObjectException;
import com.example.exception.ObjectAlreadyUsedException;
import com.example.model.Order;
import com.example.model.Product;
import com.example.repository.OrderRepository;
import com.example.serviсe.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;
    @Autowired
    ModelMapper modelMapper;

    @Test
    public void getPageTest() throws Exception {
        Order order = createOrder(1, 1);
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setProductId(Collections.singletonList(1));
        when(orderService.findAll(0, 5, false)).thenReturn(Collections.singletonList(order));

        mockMvc.perform(MockMvcRequestBuilders.
                get("/order", 0)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(objectMapper.
                        writeValueAsString(Collections.singletonList(orderDto))));
    }

    @Test
    public void createTest() throws Exception {
        Order order = createOrder(1, 1);
        when(orderService.create(order)).thenReturn(order);
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setProductId(Collections.singletonList(1));

        mockMvc.perform(MockMvcRequestBuilders.
                post("/order").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(orderDto))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(""));
    }


    @Test
    void updateTest() throws Exception {
        Order order = createOrder(1, 1);
        when(orderService.update(order)).thenReturn(order);
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setProductId(Collections.singletonList(1));

        mockMvc.perform(MockMvcRequestBuilders.
                put("/order").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(orderDto))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(""));
    }


    @Test
    public void deleteTest() throws Exception{
        Order order = createOrder(1, 1);
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setProductId(Collections.singletonList(1));

        mockMvc.perform(MockMvcRequestBuilders.
                delete("/order/{id}", 1).
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(""));;
    }

    private Order createOrder(int orderId, int productId){
        Order order = new Order();
        order.setId(orderId);
        order.setDate(LocalDate.now());
        Product product = new Product();
        product.setId(productId);
        product.setPrice(BigDecimal.valueOf(2.0));
        List<Product> products = Collections.singletonList(product);
        order.setProducts(products);
        return order;
    }

    //    @Test
//    public void createExceptionTest() throws Exception {
//        Order order = createOrder(1, 1);
//        when(orderService.create(order)).thenThrow(new ObjectAlreadyUsedException());
//        MockHttpServletResponse response = create(order);
//
//        Assertions.assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
//        Assertions.assertEquals("Such object already used",response.getContentAsString());
//    }

    //    @Test
//    public void updateExceptionTest() throws Exception {
//        Order order = createOrder(1, 1);
//        when(orderService.update(order)).thenThrow(new NoSuchObjectException());
//        MockHttpServletResponse response = update(order);
//
//        Assertions.assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
//        Assertions.assertEquals("Such object does not exist",response.getContentAsString());
//    }

//    @Test
//    public void deleteExceptionTest() throws Exception {
//        Order order = createOrder(1, 1);
//        MockHttpServletResponse response = update(order);
//        when(orderRepository.findById(order.getId())).thenThrow(new NoSuchObjectException());
//        Assertions.assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
//        Assertions.assertEquals("Such object does not exist",response.getContentAsString());
//    }



//    private MockHttpServletResponse create(Order order) throws Exception{
//        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
//        orderDto.setProductId(Collections.singletonList(1));
//
//        return mockMvc.perform(MockMvcRequestBuilders.
//                        post("/order").
//                        contentType(MediaType.APPLICATION_JSON).
//                        content(objectMapper.writeValueAsString(orderDto))).
//                andReturn().getResponse();
//    }
//
//    private MockHttpServletResponse update(Order order) throws Exception{
//        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
//        orderDto.setProductId(Collections.singletonList(1));
//        return mockMvc.perform(MockMvcRequestBuilders.
//                        put("/order").
//                        contentType(MediaType.APPLICATION_JSON).
//                        content(objectMapper.writeValueAsString(orderDto))).
//                andReturn().getResponse();
//    }
//
//    private MockHttpServletResponse delete(Order order) throws Exception{
//        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
//        orderDto.setProductId(Collections.singletonList(1));
//        return mockMvc.perform(MockMvcRequestBuilders.
//                        delete("/order/{id}", 1).
//                        contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
//    }
}
