package com.intern.project1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Entity
public class Shop_Product {
    @Id
    private Long id;
    @ManyToOne
    private Shop shop;
    @ManyToOne
    private Product product;
    private Integer quantity;
    private SimpleDateFormat createdTime;
    private SimpleDateFormat updatedTime;
}
