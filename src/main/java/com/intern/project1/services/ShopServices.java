package com.intern.project1.services;

import com.intern.project1.entities.Shop;
import com.intern.project1.entities.Shop_Product;
import com.intern.project1.entities.dto.*;
import com.intern.project1.constant.enums.Category;
import com.intern.project1.constant.enums.OrderStatus;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Optional;


public interface ShopServices {

    List<Shop_ProductDto> getShopProductsByPageIndexAndSortedBySold(String shopName, Integer pageIndex);

    List<ProductDto> getShopProductsPageIndexSortedByCreatedTimeDesc(String shopName, Integer pageIndex);

    List<ShopDto> getAllShopSortedByRatingDesc(Integer pageIndex);

    List<ShopDto> getShopsByShopCategorySortedByRatingDesc(Category category, Integer pageIndex);

    List<ShopDto> getShopsByAddressSortedByRatingDesc(String shopAddress, Integer pageIndex);

    List<ShopDto> getShopsByShopNameSortedByRatingDesc(String shopName, Integer pageIndex);

    Shop_ProductDto createShopProductObject(String shopName, ProductDto productDto, Integer productQuantity);

    boolean isShopHasThisProduct(Shop_ProductDto shopProductDto);

    Shop_ProductDto addProductToShop(Shop_ProductDto shopProductDto);

    ShopInfo updateShopInformation(ShopInfo shopInfo);

    OrderDto manageOrder(Long orderId, OrderStatus orderStatus);

    Optional<Shop_Product> getShopProduct(String shopName, String productName);

    Shop_ProductDto changeProductQuantity(Shop_ProductDto shopProductDto);

    Shop_ProductDto deceaseProduct(@Positive Integer productQuantityDecreasing, Shop_ProductDto currentShopProductDto);

    Double getShopIncome(Long shopId);

    Double getShopIncomeByMonth(Long shopId, Integer month, Integer year);

    int getAllShopNumbers();

    int getShopNumbersOfCategory(Category category);

    int getShopNumbersOfShopName(String shopName);

    List<ShopDto> getShopsByShopAddressSortedByRatingDesc(String shopAddress, int i);

    int getShopNumbersOfShopAddress(String shopAddress);

    Optional<Shop> findShopByUserName(String userName);

    ShopDto shopRegister(ShopDto shopDto);

    ShopDto getShopByUserName(String userName);


    Double getShopIncomeByMonthYear(String shopName, PeriodTime monthYear);

    Double getShopProfitByMonthYear(String shopName, PeriodTime monthYear);

    List<ShopManagement> getAllShop(int pageSelected);

    ShopManagement approveShopRequest(ShopManagement shopManagement);

    void setProfitFee();

    ShopManagement getShopByShopNameForAdmin(String shopName);

    ShopManagement changeShopStatus(ShopManagement shopManagement);

    ShopShowDto getShopByShopName(String shopName);

    Optional<ShopDto> findShopByUserId(Long userId);

    List<ShopManagement> getNotApprovedShop();
}
