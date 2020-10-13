package com.example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "sellers")
@Setter
@Getter
public class Seller {
    @Id
//    @SequenceGenerator(name = "seq_1", sequenceName = "sellers_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String LastName;
    @OneToOne(cascade = CascadeType.ALL)
    private User User;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "seller")
    private List<Product> products;

}
