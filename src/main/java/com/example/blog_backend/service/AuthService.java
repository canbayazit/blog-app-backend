package com.example.blog_backend.service;

import com.example.blog_backend.model.requestDTO.LoginRequestDTO;
import com.example.blog_backend.model.requestDTO.RefreshTokenRequestDTO;
import com.example.blog_backend.model.requestDTO.RegisterRequestDTO;
import com.example.blog_backend.model.responseDTO.JwtResponseDTO;


public interface AuthService {
    void saveUserByRole(RegisterRequestDTO registerRequestDTO);
    void saveAdminByRole(RegisterRequestDTO registerRequestDTO);
    JwtResponseDTO login(LoginRequestDTO body);
    JwtResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO);
}
