package com.example.dto;

import com.example.model.Order;
import com.example.model.Seller;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String title;
    private BigDecimal price;
    private Integer sellerId;
    private Integer orderId;
//    private Seller seller;
//    private List<Order> order;
}
