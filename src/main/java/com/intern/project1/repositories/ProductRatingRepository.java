package com.intern.project1.repositories;

import com.intern.project1.entities.Product;
import com.intern.project1.entities.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {
    @Query("select pr from ProductRating pr where pr.product.productName=?1 and pr.shop.shopName=?2 and pr.user.userName=?3")
    Optional<ProductRating> findByProductNameUserNameShopName(String productName, String shopName, String userName);

    @Query("select pr.product from ProductRating pr where pr.product.id=?1")
    Product findByProductID(Long id);

    @Modifying
    @Query("delete from ProductRating  pr where pr=?1")
    void deleteProductRatingByEntity(ProductRating productRating);
}