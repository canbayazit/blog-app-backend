package com.example.blog_backend.model.responseDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class JwtResponseDTO {
    private String accessToken;
    private UUID refreshToken;
}
