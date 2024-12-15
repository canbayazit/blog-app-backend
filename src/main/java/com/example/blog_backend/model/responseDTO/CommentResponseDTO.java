package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.dto.CommentUserDTO;
import lombok.Data;

@Data
public class CommentResponseDTO extends BaseDTO {
    private String comment;
    private CommentUserDTO user;
}
