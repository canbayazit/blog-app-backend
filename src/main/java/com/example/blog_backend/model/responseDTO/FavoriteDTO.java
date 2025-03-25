package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import lombok.Data;

@Data
public class FavoriteDTO extends BaseDTO {
    private PostDTO post;
    private UserDTO user;
}
