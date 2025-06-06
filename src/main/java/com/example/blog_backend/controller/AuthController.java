package com.example.blog_backend.controller;


import com.example.blog_backend.model.requestDTO.LoginRequestDTO;
import com.example.blog_backend.model.requestDTO.RefreshTokenRequestDTO;
import com.example.blog_backend.model.requestDTO.RegisterRequestDTO;
import com.example.blog_backend.model.responseDTO.JwtResponseDTO;
import com.example.blog_backend.service.AuthService;
import com.example.blog_backend.util.response.ApiResponse;
import com.example.blog_backend.util.security.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;
    private final JWTUtil jwtUtil;

    public AuthController(AuthService authService, JWTUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
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

    @PostMapping("refreshToken")
    public ResponseEntity<ApiResponse<JwtResponseDTO>> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        JwtResponseDTO jwtResponseDTO = authService.refreshToken(refreshTokenRequestDTO);
        // ✅ Cookie oluştur (örneğin 7 gün geçerli, HttpOnly, SameSite=strict)
        ResponseCookie refreshTokenCookie = jwtUtil.generateRefreshJwtCookie(jwtResponseDTO.getRefreshToken().toString());
        ApiResponse<JwtResponseDTO> response = ApiResponse.success(jwtResponseDTO, "Token is refreshed successfully.");
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(response);
    }
}
