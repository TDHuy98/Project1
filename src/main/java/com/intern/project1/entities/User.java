package com.intern.project1.entities;

import com.intern.project1.constant.enums.AccountStatus;
import com.intern.project1.constant.enums.Role;
import com.intern.project1.entities.token.Token;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "username can not be empty")
    @NotNull(message = "username can not be null")
    @NotBlank(message = "username can not be blank")
    @Length(min = 4, message = "username must be at least 4 character")
    private String userName;

    @NotEmpty
    @NotNull
    @Column(nullable = false)
    @NotBlank
    private String password;

    @Email
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "0+[0-9]{9}")
    @Column(unique = true)
    private String phone;

    private Date createdTime;

    private Date updatedTime;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @OneToMany(mappedBy = "user")
    private List<ProductRating> productRatings;

    @OneToMany(mappedBy = "user")
    private List<ShopRating> shopRatings;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    public String getUserName() {
        return userName;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<ProductRating> getProductRatings() {
        return productRatings;
    }

    public void setProductRatings(List<ProductRating> productRatings) {
        this.productRatings = productRatings;
    }

    public List<ShopRating> getShopRatings() {
        return shopRatings;
    }

    public void setShopRatings(List<ShopRating> shopRatings) {
        this.shopRatings = shopRatings;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", userId='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", role=").append(role);
        sb.append(", accountStatus=").append(accountStatus);
        sb.append(", productRatings=").append(productRatings);
        sb.append(", shopRatings=").append(shopRatings);
        sb.append(", tokens=").append(tokens);
        sb.append('}');
        return sb.toString();
    }
}
