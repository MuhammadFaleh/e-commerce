package com.capstone1.e_commerce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class MerchantStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "product id must not be empty")
    @Column(columnDefinition = "int")
    private Integer productID;
    @NotNull(message = "merchant id must not be empty")
    @Column(columnDefinition = "int")
    private Integer merchantID;
    @NotNull(message = "stock must not be empty")
    @Min(value = 11, message = "stock must be more than 10")
    @Column(columnDefinition = "int")
    private Integer stock;
}
