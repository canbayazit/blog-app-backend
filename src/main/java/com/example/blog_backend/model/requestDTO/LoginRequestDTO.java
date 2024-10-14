package com.example.blog_backend.model.requestDTO;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
