package com.intern.project1.entities;

import com.intern.project1.constant.enums.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.*;

import java.util.*;


@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 4)
    @Column(unique = true, nullable = false)
    private String productName;
    @PositiveOrZero
    @Column(nullable = false)
    private Double productPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category productType;
//    @Enumerated(EnumType.ORDINAL)
    private Double rating;
    private Date updatedTime;
    private Date createdTime;
    private Long sold;
    @OneToMany(mappedBy = "product")
    private List<Shop_Product> shopProducts;
    @OneToMany(mappedBy = "product")
    private List<Order_Products> ordersProducts;
    @OneToMany(mappedBy = "product")
    private List<Cart_Products_Shops> cartProducts;
    @OneToMany(mappedBy = "product")
    private List<ProductRating> productRatings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Shop_Product> getShopProducts() {
        return shopProducts;
    }

    public void setShopProducts(List<Shop_Product> shopProducts) {
        this.shopProducts = shopProducts;
    }

    public List<Order_Products> getOrdersProducts() {
        return ordersProducts;
    }

    public void setOrdersProducts(List<Order_Products> ordersProducts) {
        this.ordersProducts = ordersProducts;
    }

    public List<Cart_Products_Shops> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<Cart_Products_Shops> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public List<ProductRating> getProductRatings() {
        return productRatings;
    }

    public void setProductRatings(List<ProductRating> productRatings) {
        this.productRatings = productRatings;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productType=" + productType +
                ", rating=" + rating +
                ", updatedTime=" + updatedTime +
                ", createdTime=" + createdTime +
                ", sold=" + sold +
                ", shopProducts=" + shopProducts +
                ", orderProducts=" + ordersProducts +
                ", cartProductsShops=" + cartProducts +
                ", productRatings=" + productRatings +
                '}';
    }
}
