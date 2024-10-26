package com.example.blog_backend.model.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserPasswordUpdateRequestDTO {
    @NotBlank(message = "Password is mandatory")
    private String currentPassword;

    @NotBlank(message = "New password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String newPassword;
}
