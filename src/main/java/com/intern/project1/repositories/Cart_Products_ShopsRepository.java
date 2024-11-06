package com.intern.project1.repositories;

import com.intern.project1.entities.Cart_Products_Shops;
import com.intern.project1.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Cart_Products_ShopsRepository extends JpaRepository<Cart_Products_Shops, Long> {
    @Query("select c.product from Cart_Products_Shops c where c.cart.id=?1 order by c.addedTime")
    List<Product> findPrductsByCartId(Long id);

    @Query("select c.price from Cart c where c.id=?1")
    List<Double> getCartProductsShopsPricesByCartId(Long cartId);

    @Query("select cp.id from Cart_Products_Shops cp where cp.cart.id=?1 order by cp.updatedTime")
    List<Long> findCartProductsShopsIdsByCartId(Long cartId);

    @Query("select cp from Cart_Products_Shops cp where cp.cart.id=?1 order by cp.updatedTime")
    List<Cart_Products_Shops> findCartProductsShopsByCartId(Long cartId);

    @Query("select cps.product from Cart_Products_Shops cps where cps.cart.id=?1 and cps.shop.id=?2")
    List<Product> findProductsByCartIdAndShopId(Long cartId, Long shopId);

    @Query("select cps.product.id from Cart_Products_Shops cps where cps.cart.id=?1 and cps.shop.id=?2")
    List<Long> findProductIdsByCartIdAndShopId(Long cartId, Long shopId);

    @Query("select cps from Cart_Products_Shops cps where cps.cart.id =?1 and cps.shop.id= ?2")
    List<Cart_Products_Shops> findCart_Products_ShopsByCartIdAndShopId(Long cartId, Long shopId);

    @Modifying
    @Query("delete from Cart_Products_Shops cps where cps.cart.id=?1")
    void deleteByCartId(Long cartId);

    @Query("select cps from Cart_Products_Shops cps where cps.cart.id=?1 and cps.shop.id=?2 and cps.product.id=?3")
    Optional<Cart_Products_Shops> findByProductIdShopIdCardId(Long cartId, Long shopId, Long productId);

    @Query("select cps from Cart_Products_Shops cps where cps.cart.id =?1 and cps.shop.shopName=?2 and  cps.product.productName=?3")
    Optional<Cart_Products_Shops> findByCartIdShopNameProductName(Long cartId, String shopName, String productName);
}