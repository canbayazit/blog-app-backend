package com.example.blog_backend.util.security;

import com.example.blog_backend.model.responseDTO.ApiErrorDTO;
import com.example.blog_backend.util.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ApiErrorDTO errorDetail = ApiErrorDTO.builder()
                .fieldName(authException.getClass().getName())
                .message(authException.getMessage())
                .build();

        ApiResponse<Void> apiResponse = ApiResponse.error(
                HttpStatus.UNAUTHORIZED,
                "Authentication required",
                Collections.singletonList(errorDetail)
        );

        objectMapper.writeValue(response.getOutputStream(), apiResponse);
    }
}
