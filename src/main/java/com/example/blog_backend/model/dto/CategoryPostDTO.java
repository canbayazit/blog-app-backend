package com.example.blog_backend.model.dto;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.enums.PostStatus;
import com.example.blog_backend.model.responseDTO.CommentResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class CategoryPostDTO extends BaseDTO {
    private String title;
    private PostUserDTO author;
    private int views;
    private int likes;
    private PostStatus status;
}
