package com.intern.project1.entities.dto;

import com.intern.project1.constant.enums.OrderStatus;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private Long orderId;
    private String shopName;
    private String userName;
    private List<OrderProductsDetail> orderProductsDetail;
    @PositiveOrZero
    private Double price;
    private OrderStatus orderStatus;
    private Date createdTime;
    private Date updateTime;

}
