package com.intern.project1.entities.dto;

import com.intern.project1.constant.enums.AccountStatus;
import com.intern.project1.constant.enums.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link com.intern.project1.entities.User}
 */
@AllArgsConstructor
@Data @Builder
@ToString @NoArgsConstructor
public class UserDto implements Serializable {
    @Column(unique = true, nullable = false)
    @NotEmpty(message = "username can not be empty")
    @NotBlank(message = "username can not be blank")
    @Length(min = 4, message = "username must be at least 4 character")
    private String userName;
    @NotEmpty
    @NotBlank
    @Length(min = 6)
    private String password;
    @Email
    private String email;
    @Pattern(regexp = "0+[0-9]{9}")
    private String phone;
    private Date createdTime;
    private Date updatedTime;
    private Role role;
    private AccountStatus accountStatus;
    private List<Long> productRatingIds;
    private List<Long> shopRatingIds;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
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

    public List<Long> getProductRatingIds() {
        return productRatingIds;
    }

    public void setProductRatingIds(List<Long> productRatingIds) {
        this.productRatingIds = productRatingIds;
    }

    public List<Long> getShopRatingIds() {
        return shopRatingIds;
    }

    public void setShopRatingIds(List<Long> shopRatingIds) {
        this.shopRatingIds = shopRatingIds;
    }
}