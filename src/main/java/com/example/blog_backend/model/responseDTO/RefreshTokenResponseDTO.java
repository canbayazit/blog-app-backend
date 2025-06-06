package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import lombok.Data;

import java.time.Instant;

@Data
public class RefreshTokenResponseDTO extends BaseDTO {
    private Instant expiryDate;
    private UserDTO user;
}
