package com.intern.project1.entities.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @Column(unique = true, nullable = false)
    @NotEmpty(message = "username can not be empty")
    @NotBlank(message = "username can not be blank")
    @Length(min = 4, message = "username must be at least 4 character")
    private String userName;
    @NotEmpty
    @NotBlank
    private String password;
}
