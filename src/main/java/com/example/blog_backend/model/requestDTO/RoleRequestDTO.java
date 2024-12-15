package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.model.dto.UserDTO;
import lombok.Data;

import java.util.Set;
@Data
public class RoleRequestDTO {

    private String name;
    private String description;
}
