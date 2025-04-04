package com.example.blog_backend.model.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO<T> {
    private LocalDateTime timestamp;
    private boolean success;
    private String message;
    private T data;
    private Map<String, String> errors; // Hata detayı veya validasyon hataları
}

