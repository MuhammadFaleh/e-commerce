package com.capstone1.e_commerce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "username must not be empty")
    @Size(min = 6, message = "username must at least be 6 characters long")
    @Column(columnDefinition = "varchar(100)")
    private String username;
    @Column(columnDefinition = "varchar(40)")
    @NotBlank(message = "password must not be empty")
    @Size(min=7, message = "password must be at least 7 characters long")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$&+,:;=?@#|'<>.^*()%!-]).*$",
            message = "please enter at least one number and special character")

    private String password;
    @Column(columnDefinition = "varchar(100)")
    @NotBlank(message = "email must not be empty")
    @Email(message = "email must be valid")

    private String email;
    @Column(columnDefinition = "varchar(10)")
    @NotBlank(message = "user role must not be empty")
    @Pattern(regexp = "^(?i)(admin|customer)$", message = "role must be 'admin' or 'customer'")

    private String role;
    @Column(columnDefinition = "double")
    @NotNull(message = "user balance must not be null")
    @PositiveOrZero(message = "user balance must be a positive or zero")

    private Double balance;
}
