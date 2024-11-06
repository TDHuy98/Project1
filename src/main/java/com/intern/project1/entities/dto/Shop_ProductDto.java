package com.intern.project1.entities.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.intern.project1.entities.Shop_Product}
 */
@AllArgsConstructor
@NoArgsConstructor @Data
@ToString @Builder
public class Shop_ProductDto implements Serializable {
    private String shopName;
    private String productName;
    @PositiveOrZero
    private Integer quantity;
    @PositiveOrZero
    private Integer sold;
    private Date createdTime;
    private Date updatedTime;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
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
}