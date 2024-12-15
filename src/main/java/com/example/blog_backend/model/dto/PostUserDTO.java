package com.example.blog_backend.model.dto;

import com.example.blog_backend.core.dto.BaseDTO;
import lombok.Data;

@Data
public class PostUserDTO extends BaseDTO {
    private String firstName;
    private String lastName;
    private String username;
}
