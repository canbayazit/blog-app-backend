package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import lombok.Data;

@Data
public class UserProfileResponseDTO extends BaseDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String bio;
    private String photoUrl;
}
