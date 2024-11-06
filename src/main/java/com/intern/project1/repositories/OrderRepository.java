package com.intern.project1.repositories;

import com.intern.project1.entities.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from  Order o where o.users.id= ?1 order by o.createdTime desc")
    List<Order> findOrdersByUserIdSortedByCreatedTime(Long id, Pageable pageable);

    @Query("select o from Order o where o.shop.id=?1 order by o.createdTime desc ")
    List<Order> findOrdersByShopId(Long id);

    @Query("select o from Order o where upper(o.shop.shopName) like upper(concat('%',?1,'%') ) order by o.createdTime desc ")
    List<Order> findOrdersByShopName(String shopName);

    @Query("select o from Order o where o.shop.id=?1 order by o.createdTime desc")
    List<Order> findOrdersByShopIdSortedByCreatedTime(Long shopId, Pageable pageable);

    @Query("select o from Order o where o.shop.id=?1 and o.createdTime >= ?2 and o.createdTime<= ?3 order by o.createdTime desc ")
    List<Order> findOrdersByShopIdAndPeriod(Long shopId,
                                            Date beginTime,
                                            Date endTime,
                                            Pageable pageable);

    @Query("select o from Order o where o.shop.id=?1 and o.createdTime >= ?2 and o.createdTime<= ?3 order by o.createdTime desc ")
    List<Order> findOrdersByShopIdAndPeriod(Long shopId,
                                            Date beginTime,
                                            Date endTime);

    @Query("select o from Order o where o.users.userName=?1 and o.createdTime >= ?2 and o.createdTime<= ?3")
    List<Order> findOrdersByUserNameAndPeriod(Long userName,
                                              Date beginDate,
                                              Date endDate,
                                              Pageable pageable);

    @Query("select o.id from Order o where o.shop.id=?1 order by o.updateTime desc ")
    List<Long> findOrderIdsByShopId(Long shopId);

    @Query("select o.id from Order o where o.shop.id=?1 and o.createdTime >= ?2 and o.createdTime<= ?3")
    List<Long> findOrderIdsByShopIdAndMonthYear(Long shopId,
                                                Date beginDate,
                                                Date endDate,
                                                Pageable pageable);

    @Query("select o from Order o where o.users.id=?1 order by o.createdTime desc ")
    List<Order> findOrdersByUserId(Long id);
}
