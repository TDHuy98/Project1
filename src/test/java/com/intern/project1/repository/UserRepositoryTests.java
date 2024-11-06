package com.intern.project1.repository;

import com.intern.project1.entities.User;
import com.intern.project1.constant.enums.Role;
import com.intern.project1.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;

    @BeforeEach
    public void setUp() {
        user1 = User.builder()
                .userName("testName1")
                .email("testmail1@mail.com")
                .phone("0234567891")
                .password("password")
                .createdTime(Date.from(Instant.now()))
                .build();

        user2 = User.builder()
                .userName("testName2")
                .email("testmail2@mail.com")
                .phone("0234567892")
                .password("password")
                .createdTime(Date.from(Instant.now()))
                .build();
        user3 = User.builder()
                .userName("testName3")
                .email("testmail3@mail.com")
                .phone("0234567893")
                .password("password")
                .createdTime(Date.from(Instant.now()))
                .build();
        user4 = User.builder()
                .userName("testName4")
                .email("testmail4@mail.com")
                .phone("0234567894")
                .password("password")
                .createdTime(Date.from(Instant.now()))
                .build();
        user5 = User.builder()
                .userName("testName5")
                .email("testmail5@mail.com")
                .phone("0234567895")
                .password("password")
                .createdTime(Date.from(Instant.now()))
                .build();
    }

    @DisplayName("JUnit for save user operation")
    @Test
    public void givenMyUserObject_whenSave_thenReturnSavedUser() {
        userRepository.save(user1);
        userRepository.save(user2);
        assertThat(user1).isNotNull();
        assertThat(user1.getId()).isGreaterThan(0);
        assertThat(user2).isNotNull();
        assertThat(user2.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit for update user information operation")
    @Test
    public void givenMyUserObject_whenSave_thenReturnUpdatedUser() {
        userRepository.save(user1);
        user1.setRole(Role.SHOP);
        user1.setUpdatedTime(Date.from(Instant.now()));
        user1.setUserName("testUpdate");
        assertThat(user1).isNotNull();
        assertThat(user1.getId()).isGreaterThan(0);
        assertThat(user1.getUserName()).isEqualTo("testUpdate");
        assertThat(user1.getRole()).isEqualTo(Role.SHOP);
    }

    @DisplayName("JUnit for findAll operation")
    @Test
    public void givenUserList_whenFindAll_thenUserList() {
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        List<User> userList = userRepository.findAll().stream().toList();
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(5);
    }

    @DisplayName("JUnit for findByUserName operation")
    @Test
    public void givenUserName_whenFindByUserName_thenUserFound() {

        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);

        Optional<User> userFound = userRepository.findByUserName("testName3");

        assertThat(userFound).isPresent();
        assertThat(userFound.get().getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit for findUsersByUserName operation")
    @Test
    public void givenUserName_whenFindUsersByUserName_thenUserFoundList() {

        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);

        List<User> userFoundList = userRepository.findUsersByUserName("testName").stream().toList();

        assertThat(userFoundList).isNotNull();
        assertThat(userFoundList.size()).isEqualTo(4);
    }
}
