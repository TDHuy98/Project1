package com.intern.project1.entities.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Builder @Data
@AllArgsConstructor@NoArgsConstructor
public class ProductToCart {
    private String userName;
    private String productName;
    private String shopName;
    @Positive
    private int productQuantity;
}
