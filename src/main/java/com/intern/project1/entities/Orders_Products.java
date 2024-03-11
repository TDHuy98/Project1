package com.intern.project1.entities;

import jakarta.persistence.*;

@Entity
public class Orders_Products {
    @Id
    private Long id;
    @ManyToOne
//    @JoinColumn(name = "ordersId")
    private Order orders;
    @ManyToOne
//    @JoinColumn(name = "productId")
    private Product product;

    private Integer quantity;
}
