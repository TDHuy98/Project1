package com.intern.project1.entities;

import com.intern.project1.entities.enums.Category;
import com.intern.project1.entities.enums.Rating;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Set;


@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "product")
    private Set<Shop_Product> shopProducts;
    @OneToMany(mappedBy = "product")
    private Set<Orders_Products> ordersProducts;
    @OneToMany(mappedBy = "product")
    private Set<Cart_Products> cartProducts;
    @Column(unique = true, nullable = false)
    private String productName;
    @Column(nullable = false)
    private Float productPrice;
    @Enumerated(EnumType.STRING)
    private Category productType;
    @Enumerated(EnumType.ORDINAL)
    private Rating rating;
    @OneToMany(mappedBy = "product")
    private Set<ProductRating> productRatings;
    private SimpleDateFormat createdTime;

}
