package com.intern.project1.entities.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
@AllArgsConstructor
@Getter
@ToString
@Builder
public class LoginRequest implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 6, max = 32)
    private final String userName;
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 6, max = 32)
    private final String password;
    @Email
    private String email;
    @Pattern(regexp = "0+[0-9]{9}")
    private String phone;
}
