package com.example.blog_backend.util.security;

import com.example.blog_backend.model.responseDTO.ApiErrorDTO;
import com.example.blog_backend.util.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ApiErrorDTO errorDetail = ApiErrorDTO.builder()
                .fieldName(accessDeniedException.getClass().getName())
                .message(accessDeniedException.getMessage())
                .build();

        ApiResponse<Void> apiResponse = ApiResponse.error(
                HttpStatus.FORBIDDEN,
                "Insufficient permissions",
                Collections.singletonList(errorDetail)
        );

        objectMapper.writeValue(response.getOutputStream(), apiResponse);
    }
}