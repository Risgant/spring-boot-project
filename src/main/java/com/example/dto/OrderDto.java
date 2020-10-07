package com.example.dto;

import com.example.model.Customer;
import com.example.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private LocalDate date;
    private Integer customerId;
    private List<Integer> productId;
    private BigDecimal bynAmount;
}
