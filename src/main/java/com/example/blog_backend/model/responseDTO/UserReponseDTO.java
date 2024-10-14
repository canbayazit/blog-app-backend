package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.dto.RoleDTO;
import lombok.Data;

import java.util.Set;

@Data
public class UserReponseDTO extends BaseDTO {

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private boolean isEnable;
    private Set<RoleDTO> roles;
}
