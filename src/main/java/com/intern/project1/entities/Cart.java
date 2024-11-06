package com.intern.project1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;


import java.util.*;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "cart")
    private List<Cart_Products_Shops> cartProductsShops;
    @PositiveOrZero
    private Double price;
    private Date createdTime;
    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Cart_Products_Shops> getCartProductsShops() {
        return cartProductsShops;
    }

    public void setCartProductsShops(List<Cart_Products_Shops> cartProductsShops) {
        this.cartProductsShops = cartProductsShops;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + user +
                ", cartProductsShops=" + cartProductsShops +
                ", price=" + price +
                ", updatedTime=" + createdTime +
                '}';
    }
}
