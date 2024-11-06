package com.intern.project1.entities.dto;

import com.intern.project1.constant.enums.Category;
import com.intern.project1.constant.enums.ShopStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.intern.project1.entities.Shop}
 */
@AllArgsConstructor
@Getter
@ToString @NoArgsConstructor(force = true)
@Builder
public class ShopShowDto implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 4)
    private String shopName;
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 4)
    private String address;
    private Double shopRating;
    private Category shopCategory;
    private ShopStatus shopStatus;
    private Date createdTime;
    private Date updatedTime;
}