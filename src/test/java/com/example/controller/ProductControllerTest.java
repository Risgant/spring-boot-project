package com.example.controller;

import com.example.dto.ProductDto;
import com.example.model.Product;
import com.example.serviсe.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
import java.util.Collections;

import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService productService;
    @Autowired
    ModelMapper modelMapper;

    @Test
    public void getPageTest() throws Exception {
        Product product = createProduct(1, 2.0);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        when(productService.findAll(0, 5, false)).thenReturn(Collections.singletonList(product));

        mockMvc.perform(MockMvcRequestBuilders.
                get("/product", 0).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(objectMapper.
                                writeValueAsString(Collections.singletonList(productDto))));
    }

    @Test
    public void createTest() throws Exception {
        Product product = createProduct(1, 2.0);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        when(productService.create(product)).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.
                        post("/product").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(productDto))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(""));

    }

    @Test
    public void updateTest() throws Exception {
        Product product = createProduct(1, 2.0);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        when(productService.update(product)).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.
                        put("/product").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(productDto))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    public void deleteTest() throws Exception{
        Product product = createProduct(1, 2.0);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);

        mockMvc.perform(MockMvcRequestBuilders.
                        delete("/product/{id}", 1).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(productDto))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(""));
    }
    private Product createProduct(int id, double price){
        Product product = new Product();
        product.setId(id);
        product.setPrice(BigDecimal.valueOf(price));
        return product;
    }
}
