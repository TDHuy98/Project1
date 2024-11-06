package com.intern.project1.entities;

import com.intern.project1.constant.enums.Rating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.Date;


@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ShopRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Shop shop;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.ORDINAL)
    private Rating rating;
    private Date ratedTime;
    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
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
        return "ShopRating{" +
                "id=" + id +
                ", shop=" + shop +
                ", user=" + user +
                ", rating=" + rating +
                ", ratedTime=" + ratedTime +
                '}';
    }
}
