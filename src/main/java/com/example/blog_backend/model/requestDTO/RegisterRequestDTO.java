package com.example.blog_backend.model.requestDTO;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
