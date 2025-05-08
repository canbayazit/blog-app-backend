package com.example.blog_backend.util.response;

import com.example.blog_backend.model.enums.ApiStatus;
import com.example.blog_backend.model.responseDTO.ApiErrorDTO;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T>{
    private Instant timestamp;
    private String status; // SUCCESS, ERROR
    private int code; // HTTP status code
    private String message; // User-friendly mesaj
    private T data;
    private List<ApiErrorDTO> errors; // Detaylı hatalar

    // Success Responses
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .status(ApiStatus.SUCCESS.name())
                .code(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> created(T data, String message) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .status(ApiStatus.SUCCESS.name())
                .code(HttpStatus.CREATED.value())
                .message(message)
                .data(data)
                .build();
    }

    // Error Responses
    public static <T> ApiResponse<T> error(HttpStatus status, String message, List<ApiErrorDTO> errors) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .status(ApiStatus.ERROR.name())
                .code(status.value())
                .message(message)
                .errors(errors)
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .status(ApiStatus.ERROR.name())
                .code(status.value())
                .message(message)
                .build();
    }
}