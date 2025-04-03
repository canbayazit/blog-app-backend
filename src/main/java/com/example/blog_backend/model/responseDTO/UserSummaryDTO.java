package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.enums.UserStatus;
import lombok.Data;

import java.util.Set;

@Data
public class UserSummaryDTO extends BaseDTO {
    private String fullName;
    private String username;
    private String photoUrl;
}
