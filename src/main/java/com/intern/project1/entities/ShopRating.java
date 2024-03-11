package com.intern.project1.entities;

import com.intern.project1.entities.enums.Rating;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ShopRating {
    @Id
    private Long id;
    @ManyToOne
    private Shop shop;
    @ManyToOne
    private MyUser user;
    private Rating rating;
}
