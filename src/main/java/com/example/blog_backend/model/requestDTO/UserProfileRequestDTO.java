package com.example.blog_backend.model.requestDTO;

import lombok.Data;

@Data
public class UserProfileRequestDTO {
    private String username;
    private String bio;
    private String photoUrl;
}
