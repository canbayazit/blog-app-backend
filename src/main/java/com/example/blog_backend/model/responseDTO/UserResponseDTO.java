package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.dto.RoleDTO;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponseDTO extends BaseDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String bio;
    private String photoUrl;
    private Set<RoleDTO> roles;
}
