package com.intern.project1.entities;

import com.intern.project1.constant.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User users;
    @ManyToOne
    private Shop shop;
//    @OneToMany(mappedBy = "order")
//    private List<Order_Products> orderProducts;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private Double price;

    private Date createdTime;
    private Date updateTime;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

//    public List<Order_Products> getOrderProducts() {
//        return orderProducts;
//    }
//
//    public void setOrderProducts(List<Order_Products> orderProducts) {
//        this.orderProducts = orderProducts;
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", users=" + users +
//                ", orderProducts=" + orderProducts +
                ", orderStatus=" + orderStatus +
                ", createdTime=" + createdTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
