package com.intern.project1.entities.dto;

import com.intern.project1.constant.enums.Rating;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.intern.project1.entities.ProductRating}
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString @Builder @Data
public class ProductRatingDto implements Serializable {
    private String productName;
    private String userName;
    private String shopName;
    private Rating rating;
    private Date ratedTime;
    private Date updatedTime;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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
}