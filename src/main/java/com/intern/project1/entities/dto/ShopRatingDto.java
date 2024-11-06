package com.intern.project1.entities.dto;

import com.intern.project1.constant.enums.Rating;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.intern.project1.entities.ShopRating}
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString @Builder @Data
public class ShopRatingDto implements Serializable {
    private String shopName;
    private String userName;
    private Rating rating;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

}