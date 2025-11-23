package com.capstone1.e_commerce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "product name must not be empty")
    @Size(min = 4, message = "product name must be at least 4 characters long")
    @Column(columnDefinition = "varchar(50)")
    private String name;
    @NotNull(message = "price must not be empty")
    @Positive(message = "price must be positive value")
    @Column(columnDefinition = "double")
    private Double price;
    @NotNull(message = "category id must not be empty")
    @Column(columnDefinition = "int")
    private Integer categoryID;
}
