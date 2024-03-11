package com.intern.project1.entities;

import com.intern.project1.entities.enums.OrderStatus;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(mappedBy = "orders")
    private Set<Shop> shops;
    @ManyToOne
    private MyUser users;
    @OneToMany(mappedBy = "orders")
    private Set<Orders_Products> ordersProducts;
    private Float price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private SimpleDateFormat createdTime;
    private SimpleDateFormat updateTime;


}
