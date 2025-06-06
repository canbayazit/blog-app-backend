package com.example.blog_backend.service;

import com.example.blog_backend.entity.RefreshTokenEntity;
import com.example.blog_backend.model.responseDTO.RefreshTokenResponseDTO;

public interface RefreshTokenService {
    RefreshTokenResponseDTO createRefreshToken(String email);
    RefreshTokenEntity verifyExpiration(RefreshTokenEntity token);
}
