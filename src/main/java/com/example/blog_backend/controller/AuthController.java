package com.example.blog_backend.controller;


import com.example.blog_backend.model.requestDTO.LoginRequestDTO;
import com.example.blog_backend.model.requestDTO.RegisterRequestDTO;
import com.example.blog_backend.model.responseDTO.JwtResponseDTO;
import com.example.blog_backend.service.AuthService;
import com.example.blog_backend.util.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<JwtResponseDTO>> login(@Valid @RequestBody LoginRequestDTO body) {
        JwtResponseDTO jwtResponseDTO = authService.login(body);
        ApiResponse<JwtResponseDTO> response = ApiResponse.success(jwtResponseDTO, "Login successful.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<ApiResponse<Boolean>> register(@Valid @RequestBody RegisterRequestDTO body) {
        authService.saveUserByRole(body);
        ApiResponse<Boolean> response = ApiResponse.success(Boolean.TRUE, "User registered successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
