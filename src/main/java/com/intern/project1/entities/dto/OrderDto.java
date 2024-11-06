package com.intern.project1.entities.dto;

import com.intern.project1.constant.enums.OrderStatus;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.intern.project1.entities.Order}
 */
@AllArgsConstructor
@ToString @Data @Builder @NoArgsConstructor
public class OrderDto implements Serializable {
    private Long id;
    private Long usersId;
    private Long shopId;
//    private List<Long> orderProductIds;
    private OrderStatus orderStatus;
    private Date createdTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long usersId) {
        this.usersId = usersId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
//
//    public List<Long> getOrderProductIds() {
//        return orderProductIds;
//    }
//
//    public void setOrderProductIds(List<Long> orderProductIds) {
//        this.orderProductIds = orderProductIds;
//    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}