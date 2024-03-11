package com.intern.project1.entities;

import com.intern.project1.entities.enums.AccountStatus;
import com.intern.project1.entities.enums.Role;
import jakarta.persistence.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Set;

@Entity
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(unique = true, nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    private String eMail;
    private Long phone;
    private SimpleDateFormat createdTime;
    private SimpleDateFormat updatedTime;
    @Enumerated(EnumType.STRING)
    private Role accountRole;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    @OneToMany(mappedBy = "user")
    private Set<ProductRating> productRatings;
    @OneToMany(mappedBy = "user")
    private Set<ShopRating> shopRatings;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

}
