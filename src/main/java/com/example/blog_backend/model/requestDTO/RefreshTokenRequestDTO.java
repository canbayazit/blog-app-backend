package com.example.blog_backend.model.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RefreshTokenRequestDTO {
    @NotNull(message = "Refresh Token is mandatory")
    private UUID refreshToken;
}
