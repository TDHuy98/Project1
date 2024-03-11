package com.intern.project1.entities;

import com.intern.project1.entities.enums.Category;
import com.intern.project1.entities.enums.Rating;
import com.intern.project1.entities.enums.ShopStatus;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Set;

@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private MyUser ower;
    @OneToMany(mappedBy = "shop")
    private Set<ShopRating> shopRatings;
    @Column(unique = true, nullable = false)
    private String shopName;
    private String address;
    @ManyToMany
    @JoinTable(
            name = "orders_shop",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<Order> orders;
    @OneToMany(mappedBy = "shop")
    private Set<Shop_Product> shopProducts;
    private Boolean Approved;
    @Enumerated(EnumType.ORDINAL)
    private Rating shopRating;
    @Enumerated(EnumType.STRING)
    private Category shopCategory;
    private Float inCome;
    @Enumerated(EnumType.STRING)
    private ShopStatus shopStatus;
    private Float inComeFee;

    private SimpleDateFormat createdTime;

}
