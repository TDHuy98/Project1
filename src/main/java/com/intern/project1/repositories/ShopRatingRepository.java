package com.intern.project1.repositories;

import com.fasterxml.jackson.databind.introspect.AnnotationCollector;
import com.intern.project1.entities.Shop;
import com.intern.project1.entities.ShopRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRatingRepository extends JpaRepository<ShopRating, Long> {
    @Query("select s.shop from ShopRating s where s.user.id= ?1 order by s.shop.shopName")
    List<Shop> findShopRatingsByUserId();

    @Query("select sr from  ShopRating sr where sr.shop.id=?1 and sr.user.id=?2")
    Optional<ShopRating> findByShopIdUserId(Long shopId, Long userId);

    @Query("select sr from ShopRating sr where sr.shop.shopName=?1 and  sr.user.userName=?2")
    Optional<ShopRating> findByShopNameUserName(String userName, String shopName);
}