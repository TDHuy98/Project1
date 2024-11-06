package com.intern.project1.entities;

import com.intern.project1.constant.enums.Category;
import com.intern.project1.constant.enums.ShopStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.*;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "shop")
    private List<ShopRating> shopRatings;
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 4)
    @Column(unique = true, nullable = false)
    private String shopName;
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 4)
    private String address;
    @OneToMany(mappedBy = "shop")
    private List<Order> orders;
    @OneToMany(mappedBy = "shop")
    private List<Shop_Product> shopProducts;
    private Boolean approved;
//    @Enumerated(EnumType.ORDINAL)
    private Double shopRating;
    @Enumerated(EnumType.STRING)
    private Category shopCategory;
    private Double inCome;
    @Enumerated(EnumType.STRING)
    private ShopStatus shopStatus;
    private Double profitFee;
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

    public List<ShopRating> getShopRatings() {
        return shopRatings;
    }

    public void setShopRatings(List<ShopRating> shopRatings) {
        this.shopRatings = shopRatings;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Shop_Product> getShopProducts() {
        return shopProducts;
    }

    public void setShopProducts(List<Shop_Product> shopProducts) {
        this.shopProducts = shopProducts;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Double getShopRating() {
        return shopRating;
    }

    public void setShopRating(Double shopRating) {
        this.shopRating = shopRating;
    }

    public Category getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(Category shopCategory) {
        this.shopCategory = shopCategory;
    }

    public Double getInCome() {
        return inCome;
    }

    public void setInCome(Double inCome) {
        this.inCome = inCome;
    }

    public ShopStatus getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(ShopStatus shopStatus) {
        this.shopStatus = shopStatus;
    }

    public Double getProfitFee() {
        return profitFee;
    }

    public void setProfitFee(Double profitFee) {
        this.profitFee = profitFee;
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
        final StringBuffer sb = new StringBuffer("Shop{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", shopRatings=").append(shopRatings);
        sb.append(", shopName='").append(shopName).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", orders=").append(orders);
        sb.append(", shopProducts=").append(shopProducts);
        sb.append(", approved=").append(approved);
        sb.append(", shopCategory=").append(shopCategory);
        sb.append(", inCome=").append(inCome);
        sb.append(", shopStatus=").append(shopStatus);
        sb.append(", profitFee=").append(profitFee);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append('}');
        return sb.toString();
    }
}
