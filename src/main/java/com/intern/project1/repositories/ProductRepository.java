package com.intern.project1.repositories;

import com.intern.project1.entities.Product;
import com.intern.project1.constant.enums.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p order by p.createdTime desc ")
    List<Product> findAllOrderByCreatedTimeDesc();

    @Query("select p from Product p order by p.sold desc ")
    List<Product> findAllOrderBySoldDesc();

    @Query("select p from Product p where upper(p.productName) like upper(concat('%',?1,'%'))")
    Optional<Product> findByProductName(String productName);

    @Query("select p from Product p where ltrim(rtrim(upper(p.productName))) like ltrim(rtrim(upper(concat('%',?1,'%')))) order by p.sold desc ")
    List<Product> findProductsByNameOrderBySoldDesc(String productName);

    @Query("select p from Product p where (upper(p.productName))like (upper(concat('%',?1,'%'))) order by p.sold desc ")
    List<Product> findProductsByNameOrderBySoldDesc(String productName, Pageable pageable);

    @Query("select p from Product p where ltrim(rtrim(upper(p.productName))) like ltrim(rtrim(upper(concat('%',?1,'%')))) order by p.createdTime desc ")
    List<Product> findProductsByNameOrderByCreatedTimeDesc(String productName);

    @Query("select p from Product p where ltrim(rtrim(upper(p.productName))) like ltrim(rtrim(upper(concat('%',?1,'%')))) order by p.createdTime desc ")
    List<Product> findProductsByNameOrderByCreatedTimeDesc(String productName, Pageable pageable);

    @Query("select p from Product p where p.productType = ?1 order by p.createdTime desc ")
    List<Product> findProductsByCategorySortedByCreatedTimeDesc(Category category, Pageable pageable);

    @Query("select p from Product p where p.productType = ?1 order by p.sold desc ")
    List<Product> findProductsByCategorySortedBySoldDesc(Category category, Pageable pageable);

    @Query("select p from Product p where p.productType = ?1 order by p.sold desc ")
    List<Product> findProductsByCategorySortedBySoldDesc(Category category);

    @Query("select p from Product p where p.productType = ?1 order by p.createdTime desc ")
    List<Product> findProductsByCategorySortedByCreatedTimeDesc(Category category);

    @Query("select p from Product p where p.createdTime >= :beginTime and p.createdTime<=:endTime")
    List<Product> findProductsByYearMonth(@Param("beginTime") Date beginTime,
                                        @Param("endTime") Date endTime,
                                        Pageable pageable);
}
