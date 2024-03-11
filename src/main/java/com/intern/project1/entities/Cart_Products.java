package com.intern.project1.entities;

import jakarta.persistence.*;

@Entity
public class Cart_Products {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cart_Id")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "product_Id")
    private Product product;

    private Integer quantity;
}
