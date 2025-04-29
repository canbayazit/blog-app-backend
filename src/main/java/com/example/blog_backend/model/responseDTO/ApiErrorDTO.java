package com.example.blog_backend.model.responseDTO;

import lombok.*;

// ya constructor ile yada builder ile set et
@Getter
@Builder
@AllArgsConstructor
public class ApiErrorDTO {
    private String fieldName; // Hangi alanda hata varsa
    private String message; // Developer friendly mesaj
}
