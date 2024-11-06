package com.intern.project1.entities.dto;

import com.intern.project1.constant.enums.Category;
import com.intern.project1.constant.enums.ShopStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Builder
@Getter
@AllArgsConstructor
public class ShopInfo {
    private final String userName;
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 4)
    private final String shopName;
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 4)
    private final String address;

    private final Category shopCategory;
    private final ShopStatus shopStatus;
    private final Date createdTime;
    private final Date updatedTime;
}
