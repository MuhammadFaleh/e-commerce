package com.capstone1.e_commerce.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotBlank(message = "product id must not be empty")
    private String id;
    @NotBlank(message = "product name must not be empty")
    @Size(min = 4, message = "product name must be at least 4 characters long")
    private String name;
    @NotNull(message = "price must not be empty")
    @Positive(message = "price must be positive value")
    private double price;
    @NotBlank(message = "category id must not be empty")
    private String categoryID;
}
