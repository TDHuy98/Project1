package com.intern.project1.repositories;

import com.intern.project1.entities.Cart;
import com.intern.project1.entities.User;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select c from Cart c where c.user.id=?1")
    Optional<Cart> findByUserId(Long userId);

    @Query("select c from Cart c where upper(c.user.userName) like upper(:userName) ")
    Optional<Cart> findByUserName(@Param("userName") String userName);

    @Query("select c.id from Cart c where upper(c.user.userName) like upper(:userName)")
    Long findCartIdByUserName(@Param("userName") String userName);
}
