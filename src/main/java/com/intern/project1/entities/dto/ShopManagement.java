package com.intern.project1.entities.dto;

import com.intern.project1.constant.enums.Category;
import com.intern.project1.constant.enums.ShopStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopManagement implements Serializable {
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
    private List<String> shopProductName;
    private Boolean approved;
    private Category shopCategory;
    private ShopStatus shopStatus;
    private Double profitFee;
    private Date createdTime;
    private Date updatedTime;

    public String getUserName() {
        return userName;
    }

    public List<Long> getShopRatingIds() {
        return shopRatingIds;
    }

    public String getShopName() {
        return shopName;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getShopProductName() {
        return shopProductName;
    }

    public Boolean getApproved() {
        return approved;
    }

    public Category getShopCategory() {
        return shopCategory;
    }

    public ShopStatus getShopStatus() {
        return shopStatus;
    }

    public Double getProfitFee() {
        return profitFee;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }
}
