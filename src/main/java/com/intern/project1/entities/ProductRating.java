package com.intern.project1.entities;

import com.intern.project1.constant.enums.Rating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;
    @ManyToOne
    private User user;
    @ManyToOne
    private Shop shop;
    @Enumerated(EnumType.ORDINAL)
    private Rating rating;
    private Date ratedTime;
    private Date updatedTime;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Date getRatedTime() {
        return ratedTime;
    }

    public void setRatedTime(Date ratedTime) {
        this.ratedTime = ratedTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "ProductRating{" +
                "id=" + id +
                ", product=" + product +
                ", user=" + user +
                ", rating=" + rating +
                ", ratedTime=" + ratedTime +
                '}';
    }
}
