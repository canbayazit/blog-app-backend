package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.enums.UserStatus;
import lombok.Data;
import java.util.Set;

@Data
public class UserDTO extends BaseDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String bio;
    private String photoUrl;
    private UserStatus status;
    private Set<RoleDTO> roles;
}
