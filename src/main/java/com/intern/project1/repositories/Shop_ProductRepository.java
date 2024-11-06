package com.intern.project1.repositories;

import com.intern.project1.entities.Product;
import com.intern.project1.entities.Shop_Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Shop_ProductRepository extends JpaRepository<Shop_Product, Long> {
    @Query("select sp from Shop_Product sp where sp.shop.shopName = ?1 order by sp.product.sold desc ")
    List<Shop_Product> findByShopNameOrderBySoldDesc(String shopName, Pageable pageable);

    @Query("select sp.product from Shop_Product sp where sp.shop.id = ?1 order by sp.createdTime desc ")
    List<Product> findProductsByShopIdOrderByCreatedTimeDesc(Long shopId, Pageable pageable);

    @Query("select sp from Shop_Product sp where sp.product.productName=:productName and sp.shop.shopName=:shopName")
    Optional<Shop_Product> findByProductNameAndShopName(@Param("productName") String productName,
                                                        @Param("shopName") String shopName);

    @Query("select sp from Shop_Product sp where sp.product.id=:productId and sp.shop.id=:shopId")
    Optional<Shop_Product> findByProductIdAndShopID(@Param("productId")Long productId,
                                                        @Param("shopId") Long shopId);
    @Query("select sp from Shop_Product sp where (upper(sp.product.productName))like (upper(concat('%',?1,'%'))) order by sp.sold desc ")
    List<Shop_Product> findByProductNameOrderBySoldDesc(String productName, Pageable pageable);
}