package com.intern.project1.entities.dto;

import com.intern.project1.constant.enums.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * DTO for {@link com.intern.project1.entities.User}
 */
@AllArgsConstructor
@Getter
@ToString @Builder
public class RegisterRequest implements Serializable {
    @Column(unique = true, nullable = false)
    @NotEmpty(message = "username can not be empty")
    @NotNull(message = "username can not be null")
    @NotBlank(message = "username can not be blank")
    @Length(min = 4, message = "username must be at least 4 character")
    private final String userName;
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 6, max = 32)
    private final String password;
    @Email
    private final String email;
    @Pattern(regexp = "0+[0-9]{9}")
    private final String phone;
    private final Role role;
}