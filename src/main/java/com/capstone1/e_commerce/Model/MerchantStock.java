package com.capstone1.e_commerce.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotBlank(message = "merchant stock id must not be empty")
    private String id;
    @NotBlank(message = "product id must not be empty")
    private String productID;
    @NotBlank(message = "merchant id must not be empty")
    private String merchantID;
    @NotNull(message = "stock must not be empty")
    @Min(value = 11, message = "stock must be more than 10")
    private int stock;
}
