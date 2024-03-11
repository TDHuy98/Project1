package com.intern.project1.entities;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Set;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "cart")
    private MyUser user;
    @OneToMany(mappedBy = "cart")
    private Set<Cart_Products> cartProducts;
    private Float price;
    private SimpleDateFormat lastTime;
}
