package com.capstone1.e_commerce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "merchant name must not be empty")
    @Size(min = 4, message = "merchant name should be at least 4 characters long")
    @Column(columnDefinition = "varchar(50)")
    private String name;
}
