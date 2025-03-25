package com.example.blog_backend.model.requestDTO;

import lombok.Data;

import java.util.Set;

@Data
public class RoleRequestDTO {
    private String name;
    private String description;
    private Set<UserRequestDTO> users;
}
