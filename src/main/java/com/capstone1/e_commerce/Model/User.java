package com.capstone1.e_commerce.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    @NotBlank(message = "user id must not be empty")
    private String id;
    @NotBlank(message = "username must not be empty")
    @Size(min = 6, message = "username must at least be 6 characters long")
    private String username;
    @NotBlank(message = "password must not be empty")
    @Size(min=7, message = "password must be at least 7 characters long")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$&+,:;=?@#|'<>.^*()%!-]).*$",
            message = "please enter at least one number and special character")
    private String password;
    @NotBlank(message = "email must not be empty")
    @Email(message = "email must be valid")
    private String email;
    @NotBlank(message = "user role must not be empty")
    @Pattern(regexp = "^(?i)(admin|customer)$", message = "role must be 'admin' or 'customer'")
    private String role;
    @NotNull(message = "user balance must not be null")
    @PositiveOrZero(message = "user balance must be a positive or zero")
    private double balance;
}
