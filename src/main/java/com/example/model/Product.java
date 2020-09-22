package com.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @SequenceGenerator(name = "seq_1", sequenceName = "products_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_1")
    private Integer id;
    private String title;
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.EAGER)
    private Seller seller;
    @ManyToOne(fetch = FetchType.EAGER)
    private Order order;
}
