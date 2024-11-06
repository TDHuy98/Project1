package com.intern.project1.entities.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.intern.project1.entities.Shop_Product}
 */
@AllArgsConstructor
@Getter @Setter
@ToString @Builder @NoArgsConstructor
public class ShopProductShowDto implements Serializable {
    private String shopName;
    private String productName;
    @PositiveOrZero
    private Double productPrice;
    @PositiveOrZero
    private Integer quantity;
    @PositiveOrZero
    private Integer sold;
    private Date createdTime;
    private Date updatedTime;
}