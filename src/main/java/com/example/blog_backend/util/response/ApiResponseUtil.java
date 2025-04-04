package com.example.blog_backend.util.response;

import com.example.blog_backend.model.responseDTO.ApiResponseDTO;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiResponseUtil {
    // Başarılı durum response'u
    public static <T> ApiResponseDTO<T> success(T data, String message) {
        ApiResponseDTO<T> response = new ApiResponseDTO<>();
        response.setTimestamp(LocalDateTime.now());
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        // errors boş kalabilir
        return response;
    }

    // Hata durum response'u
    public static <T> ApiResponseDTO<T> error(String message, Map<String, String> errors) {
        ApiResponseDTO<T> response = new ApiResponseDTO<>();
        response.setTimestamp(LocalDateTime.now());
        response.setSuccess(false);
        response.setMessage(message);
        response.setErrors(errors);
        // data bu senaryoda boş kalacak
        return response;
    }

    public static <T> ApiResponseDTO<T> error(String message) {
        return error(message, null);
    }
}
