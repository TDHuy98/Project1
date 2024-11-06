package com.intern.project1.entities.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.intern.project1.entities.Order_Products}
 */
@AllArgsConstructor
@Getter @Builder
@ToString @NoArgsConstructor
public class OrderProductsDetail implements Serializable {
//    private Long orderId;
    private String productProductName;
    @PositiveOrZero
    private Integer quantity;
    @PositiveOrZero
    private Double price;
    private Date createdTime;
    private Date updatedTime;
}