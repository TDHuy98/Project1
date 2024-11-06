package com.intern.project1.repositories;

import com.intern.project1.entities.Shop;
import com.intern.project1.entities.dto.ShopDto;
import com.intern.project1.constant.enums.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    @Query("select s from Shop s where upper(s.shopName) like upper(:shopName) " +
            "and s.approved=true and s.shopStatus = 'ACTIVATED' ")
    Optional<Shop> findShopByShopName(@Param("shopName") String shopName);

    @Query("select s from Shop s where upper(s.shopName) like upper(:shopName)")
    Optional<Shop> findShopByShopNameAdmin(@Param("shopName") String shopName);

    @Query("select s.id from Shop s where upper(s.shopName) like upper(:shopName) ")
    Long findShopIdByShopName(@Param("shopName") String shopName);

    @Query("select s from Shop s where s.approved=true and s.shopStatus = 'ACTIVATED'order by s.shopRating desc ")
    List<Shop> findAllShopOrderByRatingDesc(Pageable pageable);

    @Query("select s from Shop s order by s.shopRating asc ")
    List<Shop> findAllShopOrderByRatingAsc();

    @Query("select s from Shop s where upper(s.shopName) like upper(concat('%',?1,'%') ) order by s.inCome desc ")
    List<Shop> findShopsByShopNameOrderByInComeDesc();

    @Query("select s from Shop s where upper(s.shopName) like upper(concat('%',?1,'%') ) order by s.inCome asc ")
    List<Shop> findShopsByShopNameOrderByInComeAsc();

    @Query("select s from Shop s where upper(s.shopName) like upper(concat('%',:shopName,'%') ) and s.approved=true and s.shopStatus = 'ACTIVATED'  order by s.shopRating desc ")
    List<Shop> findShopsByShopNameOrderByShopRatingDesc(@Param("shopName") String shopNam, Pageable pageble);

    @Query("select s from Shop s where upper(s.shopName) like upper(concat('%',:shopName,'%') ) order by s.shopRating asc ")
    List<Shop> findShopsByShopNameOrderByShopRatingAsc(@Param("shopName") String shopNam, Pageable pageble);

    @Query("select s from Shop s where s.shopCategory = ?1 order by s.createdTime desc ")
    List<Shop> findShopsByCategorySortedByCreatedTimeDesc(Category category, Pageable pageable);

    @Query("select s from Shop s where s.shopCategory = ?1 and s.approved=true and s.shopStatus = 'ACTIVATED'  order by s.shopRating desc ")
    List<Shop> findShopsByCategorySortedByShopRatingDesc(Category category, Pageable pageable);

    @Query("select s from Shop s where upper(s.address) like upper(:shopAddress) and s.approved=true and s.shopStatus = 'ACTIVATED'  order by s.shopRating desc ")
    List<Shop> findShopsByAddressSortedByShopRatingDesc(@Param("shopAddress") String shopAddress, Pageable pageable);

    @Query("select s from Shop s where s.shopCategory = :category and s.approved=true and s.shopStatus = 'ACTIVATED' ")
    List<Shop> findShopsByCategoty(@Param("category") Category category);

    @Query("select s from Shop s where upper(s.shopName) like upper(concat('%',:shopName,'%') )and s.approved=true and s.shopStatus = 'ACTIVATED' ")
    List<Shop> findShopsByShopName(String shopName);

    @Query("select s from Shop s where upper(s.address) like upper(?1) and s.approved=true and s.shopStatus = 'ACTIVATED' ")
    List<Shop> findShopsByShopAddress(String address);

    @Query("select s from Shop s where s.shopName=?1 and s.approved=true and s.shopStatus = 'ACTIVATED' ")
    Optional<Shop> findByShopName(String shopName);

    @Query("select s from Shop s where s.user.userName=?1")
    Optional<Shop> findByUserName(String userName);

    @Query("select s from Shop s where s.user.id=?1")
    Optional<ShopDto> findShopByUserId(Long userId);

    @Query("select s from Shop s where s.approved=false order by s.createdTime asc")
    List<Shop> findNotApprovedShop();
}
