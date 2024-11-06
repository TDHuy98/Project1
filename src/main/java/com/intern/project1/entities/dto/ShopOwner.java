package com.intern.project1.entities.dto;

import com.intern.project1.constant.enums.Category;
import com.intern.project1.constant.enums.ShopStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

public class ShopOwner implements Serializable {
    private String userName;
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
    private Category shopCategory;
    private Double inCome;
    private ShopStatus shopStatus;
    private Double profitFee;
    private Date createdTime;
    private Date updatedTime;
}
