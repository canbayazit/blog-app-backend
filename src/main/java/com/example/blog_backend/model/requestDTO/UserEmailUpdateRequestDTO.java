package com.example.blog_backend.model.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserEmailUpdateRequestDTO {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;
}
