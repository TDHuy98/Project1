package com.intern.project1.repositories;

import com.intern.project1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where upper(u.userName) like upper(?1) ")
    Optional<User> findByUserName(String username);

    @Query("select u from User u where upper(u.userName) like upper(concat('%',?1,'%'))")
    List<User> findUsersByUserName(String username);

    @Query("select u from User u where u.phone like (?1) ")
    Optional<User> findByPhone(String phone);

    @Query("select u from User u where upper(u.email) like upper(?1) ")
    Optional<User> findByEmail(String email);


}
