package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.dto.UserDTO;
import lombok.Data;

import java.util.Set;

@Data
public class RoleResponseDTO extends BaseDTO {
    private String name;
    private String description;
    private Set<UserDTO> users;
}
