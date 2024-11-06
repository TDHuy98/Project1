package com.intern.project1.services;

import com.intern.project1.entities.*;
import com.intern.project1.entities.dto.*;

import java.util.List;
import java.util.Optional;

public interface UserServices {

    List<ProductDto> defaultPage();

    UserDto createNewUser(UserDto userDto);

    List<OrderDto> getOrdersByUserIdPagedSortedByCreatedTime(Long userId, Integer pageIndex);

    Optional<User> findByUserName(String username);

    CartDto checkUserExistThenAddToCart(ProductToCart product);

    Optional<User> getUserById(Long userId);

    ProductRatingDto rateProduct(ProductRatingDto productRatingDto);


    Optional<ShopRating> getRatedShop(String shopName, String userName);

    ShopRatingDto rateShop(ShopRatingDto shopRatingDto);

    void removeRateShop(ShopRatingDto shopRatingDto);

    ShopRatingDto updateRateShop(ShopRatingDto shopRatingDto);

    Optional<User> getUserByName(String userName);

    Optional<ProductRating> getRatedProduct(String shopName, String userName, String productName);

    ProductRatingDto updateRateProduct(ProductRatingDto productRatingDto);

    void updateRatingProduct(Product product);

    void updateRatingShop(String shopName);

    void removeRateProduct(ProductRatingDto productRatingDto);
}
