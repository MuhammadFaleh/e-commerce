package com.capstone1.e_commerce.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {
    @NotBlank(message = "merchant id must not be empty")
    private String id;
    @NotBlank(message = "merchant name must not be empty")
    @Size(min = 4, message = "merchant name should be at least 4 characters long")
    private String name;
}
