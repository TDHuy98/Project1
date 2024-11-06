package com.intern.project1.repositories;

import com.intern.project1.entities.Order_Products;
import com.intern.project1.entities.Product;
import com.intern.project1.entities.dto.OrderDetailDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface Order_ProductsRepository extends JpaRepository<Order_Products, Long> {
    @Query("select o.product from Order_Products o where o.order.id=?1 order by o.order.createdTime desc ")
    List<Product> findProductsByOrderId(Long id);

    @Query("select op from Order_Products op where op.order.id=?1 order by op.createdTime")
    List<Order_Products> findOrderProductsByOrderId(Long id);

    @Query("select op from Order_Products op where op.order.id = :orderId and op.createdTime >= :begin and op.createdTime<= :end")
    List<Order_Products> findOrderProductsByOrderIdAndPeriod(@Param("orderId") Long orderId,
                                                             @Param("begin") Date beginDate,
                                                             @Param("end") Date endDate);
//@Query("select o from Order o, Order_Products op where o.users.id=?1 order by op.order.createdTime")
//    List<Order_Products> findOrde(Long userId);
}