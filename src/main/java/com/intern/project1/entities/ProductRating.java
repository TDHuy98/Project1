package com.intern.project1.entities;

import com.intern.project1.entities.enums.Rating;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.apache.catalina.User;

@Entity
public class ProductRating {
    @Id
    private Long id;

    @ManyToOne
    private Product product;
    @ManyToOne
    private MyUser user;
    private Rating rating;
}
