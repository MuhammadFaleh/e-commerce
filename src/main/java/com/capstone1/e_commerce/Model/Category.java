package com.capstone1.e_commerce.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {
    @NotBlank(message = "category id must not be empty")
    private String id;
    @NotBlank(message = "category name must not be empty")
    @Size(min = 4, message = "category name must be at least 4 characters")
    private String name;
}
