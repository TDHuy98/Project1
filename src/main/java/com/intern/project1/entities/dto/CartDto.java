package com.intern.project1.entities.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link com.intern.project1.entities.Cart}
 */
@AllArgsConstructor
@Data @NoArgsConstructor
@ToString @Builder 
public class CartDto implements Serializable {
    private String userName;
    private List<Cart_Products_ShopsDto> cartProductsShopsDtos;
    @PositiveOrZero
    private Double price;
    private Date createdTime;
    private Date updatedTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Cart_Products_ShopsDto> getCartProductsShopsDtos() {
        return cartProductsShopsDtos;
    }

    public void setCartProductsShopsDtos(List<Cart_Products_ShopsDto> cartProductsShopsDtos) {
        this.cartProductsShopsDtos = cartProductsShopsDtos;
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
}