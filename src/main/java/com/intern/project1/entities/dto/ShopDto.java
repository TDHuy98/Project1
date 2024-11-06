package com.intern.project1.entities.dto;

import com.intern.project1.constant.enums.Category;
import com.intern.project1.constant.enums.ShopStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link com.intern.project1.entities.Shop}
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class ShopDto implements Serializable {
    private String userName;
    private List<Long> shopRatingIds;
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 4)
    private String shopName;
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 4)
    private String address;
    private List<Long> orderIds;
    private List<Long> shopProductIds;
    private Boolean approved;
    private Double shopRating;
    private Category shopCategory;
    private Double inCome;
    private ShopStatus shopStatus;
    private Double profitFee;
    private Date createdTime;
    private Date updatedTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Long> getShopRatingIds() {
        return shopRatingIds;
    }

    public void setShopRatingIds(List<Long> shopRatingIds) {
        this.shopRatingIds = shopRatingIds;
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

    public List<Long> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<Long> orderIds) {
        this.orderIds = orderIds;
    }

    public List<Long> getShopProductIds() {
        return shopProductIds;
    }

    public void setShopProductIds(List<Long> shopProductIds) {
        this.shopProductIds = shopProductIds;
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
}