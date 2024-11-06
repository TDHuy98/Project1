package com.intern.project1.repository;

import com.intern.project1.entities.Cart;
import com.intern.project1.entities.User;
import com.intern.project1.constant.enums.AccountStatus;
import com.intern.project1.constant.enums.Role;
import com.intern.project1.repositories.CartRepository;
import com.intern.project1.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CartRepositoryTest {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
private User user1;
private Cart cart1;
    @BeforeEach
    public void setUp() {
        user1 = User.builder()
                .userName("usertest1")
                .password("password1")
                .phone("0123456780")
                .email("test1@mail.com")
                .accountStatus(AccountStatus.ACTIVATED)
                .role(Role.CUSTOMER)
                .createdTime(Date.from(Instant.now()))
                .build();
        cart1 = Cart.builder()
                .user(user1)
                .price(0.0)
                .createdTime(Date.from(Instant.now()))
                .build();
    }

    @DisplayName("JUnit save cart operation")
    @Test
    public void givenCartObject_whenSaveCart_thenReturnCartObject() {
        cartRepository.save(cart1);

        assertThat(cart1.getId()).isGreaterThan(0);
        assertThat(cart1).isNotNull();

    }
}
