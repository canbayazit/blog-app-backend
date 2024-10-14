package com.example.blog_backend.model.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private boolean isEnable;
}
