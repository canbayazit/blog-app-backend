package com.example.blog_backend.controller;


import com.example.blog_backend.model.enums.RoleType;
import com.example.blog_backend.model.requestDTO.LoginRequestDTO;
import com.example.blog_backend.model.requestDTO.RegisterRequestDTO;
import com.example.blog_backend.service.AuthService;
import com.example.blog_backend.util.security.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;
    private final AuthService authService;

    public AuthController(AuthenticationManager authManager, JWTUtil jwtUtil, AuthService authService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @PostMapping("login")
    public Map<String, Object> login(@Valid @RequestBody LoginRequestDTO body) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

        authManager.authenticate(authInputToken);

        String token = jwtUtil.generateToken(body.getEmail());

        Map<String, Object> authorizationMap = new HashMap<>();
        authorizationMap.put("jwt-token", token);

        return authorizationMap;
    }

    @PostMapping("register")
    public ResponseEntity<Boolean> register(@Valid @RequestBody RegisterRequestDTO body) {
        authService.saveUserByRole(body, RoleType.USER);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @PostMapping("admin/login")
    public Map<String, Object> adminLogin(@Valid @RequestBody LoginRequestDTO body) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

        authManager.authenticate(authInputToken);

        String token = jwtUtil.generateToken(body.getEmail());

        Map<String, Object> authorizationMap = new HashMap<>();
        authorizationMap.put("jwt-token", token);

        return authorizationMap;
    }

    @PostMapping("admin/register")
    public ResponseEntity<Boolean> adminRegister(@Valid @RequestBody RegisterRequestDTO body) {
        authService.saveUserByRole(body, RoleType.ADMIN);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
