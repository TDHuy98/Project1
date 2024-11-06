package com.intern.project1.entities.dto;

import com.intern.project1.constant.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link com.intern.project1.entities.Product}
 */
@AllArgsConstructor
@NoArgsConstructor @Builder
@ToString @Data
public class ProductDto implements Serializable {
    @NotEmpty
    @NotBlank
    @Length(min = 4)
    private String productName;
    @PositiveOrZero
    private Double productPrice;
    private Category productType;
    private Double rating;
    private Date updatedTime;
    private Date createdTime;
    private Long sold;
    private List<Long> shopProductIds;
    private List<Long> ordersProductIds;
    private List<Long> cartProductIds;
    private List<Long> productRatingIds;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Category getProductType() {
        return productType;
    }

    public void setProductType(Category productType) {
        this.productType = productType;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getSold() {
        return sold;
    }

    public void setSold(Long sold) {
        this.sold = sold;
    }

    public List<Long> getShopProductIds() {
        return shopProductIds;
    }

    public void setShopProductIds(List<Long> shopProductIds) {
        this.shopProductIds = shopProductIds;
    }

    public List<Long> getOrdersProductIds() {
        return ordersProductIds;
    }

    public void setOrdersProductIds(List<Long> ordersProductIds) {
        this.ordersProductIds = ordersProductIds;
    }

    public List<Long> getCartProductIds() {
        return cartProductIds;
    }

    public void setCartProductIds(List<Long> cartProductIds) {
        this.cartProductIds = cartProductIds;
    }

    public List<Long> getProductRatingIds() {
        return productRatingIds;
    }

    public void setProductRatingIds(List<Long> productRatingIds) {
        this.productRatingIds = productRatingIds;
    }
}