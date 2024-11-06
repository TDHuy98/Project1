package com.intern.project1.services;

import com.intern.project1.entities.Cart;
import com.intern.project1.entities.dto.CartDto;
import com.intern.project1.entities.dto.Cart_Products_ShopsDto;
import com.intern.project1.entities.dto.ProductToCart;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface CartServices {
    Cart initCart(Long userId);
    Optional<CartDto> getCart(String userName);
    CartDto addProductToCart(ProductToCart product);

    Cart_Products_ShopsDto deleteProductFromCart(Cart_Products_ShopsDto cartProductsShopsDto);

    CartDto changeQuantity(Cart_Products_ShopsDto cps);
}
