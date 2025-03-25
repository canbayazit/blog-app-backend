package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import lombok.Data;

@Data
public class FavoriteRequestDTO extends BaseRequestDTO {
    private PostRequestDTO post;
    private UserRequestDTO user;
}
