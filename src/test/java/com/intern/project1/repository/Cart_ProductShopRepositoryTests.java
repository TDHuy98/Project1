package com.intern.project1.repository;

import com.intern.project1.entities.Cart;
import com.intern.project1.entities.Cart_Products_Shops;
import com.intern.project1.entities.Product;
import com.intern.project1.entities.User;
import com.intern.project1.constant.enums.AccountStatus;
import com.intern.project1.constant.enums.Category;
import com.intern.project1.constant.enums.Role;
import com.intern.project1.repositories.CartRepository;
import com.intern.project1.repositories.Cart_Products_ShopsRepository;
import com.intern.project1.repositories.ProductRepository;
import com.intern.project1.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class Cart_ProductShopRepositoryTests {
    @Autowired
    private Cart_Products_ShopsRepository cartProductsRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("JUnit findProductsByCartId operation")
    @Test
    public void givenCartId_whenFindProductsByCartId_thenReturnProductList() {
        User user = User.builder()
                .userName("usertest5")
                .password("password1")
                .phone("0123456789")
                .email("test@mail.com")
                .accountStatus(AccountStatus.ACTIVATED)
                .role(Role.CUSTOMER)
                .createdTime(Date.from(Instant.now()))
                .build();
        userRepository.save(user);
        Product p1 = Product.builder()
                .id(1L)
                .productName("Product1")
                .productPrice(1.0)
                .productType(Category.TOY)
                .createdTime(Date.from(Instant.now()))
                .build();
        Product p2 = Product.builder()
                .id(2L)
                .productName("Product2")
                .productPrice(1.0)
                .productType(Category.HOME_LIVING)
                .createdTime(Date.from(Instant.now()))
                .build();
        Product p3 = Product.builder()
                .id(3L)
                .productName("Product3")
                .productPrice(1.0)
                .productType(Category.COMPUTER_ACCESSORIES)
                .createdTime(Date.from(Instant.now()))
                .build();
        Product p4 = Product.builder()
                .id(4L)
                .productName("Product4")
                .productPrice(1.0)
                .productType(Category.CLOTHES)
                .createdTime(Date.from(Instant.now()))
                .build();
        Product p5 = Product.builder()
                .id(5L)
                .productName("Product5")
                .productPrice(1.0)
                .productType(Category.MOBILE_GADGETS)
                .createdTime(Date.from(Instant.now()))
                .build();
        Product p6 = Product.builder()
                .id(6L)
                .productName("Product6")
                .productPrice(1.0)
                .productType(Category.CONSUMER_ELECTRONICS)
                .createdTime(Date.from(Instant.now()))
                .build();
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);
        productRepository.save(p5);
        productRepository.save(p6);
        Cart cart = Cart.builder()
                .id(1L)
                .user(user)
                .createdTime(Date.from(Instant.now()))
                .build();
        cartRepository.save(cart);
        Cart_Products_Shops cartProducts = Cart_Products_Shops.builder()
                .id(1L)
                .cart(cart)
                .product(p1)
                .addedTime(Date.from(Instant.now()))
                .quantity(10)
                .build();
        cartProductsRepository.save(cartProducts);

        List<Product> productList=cartProductsRepository.findPrductsByCartId(cart.getId());

        assertThat(productList.size()).isEqualTo(1);
        assertThat(productList).isNotNull();
        assertThat(user).isNotNull();
        assertThat(user.getId()).isGreaterThan(0);
    }
}
