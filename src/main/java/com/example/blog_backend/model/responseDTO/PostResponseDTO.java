package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.dto.CategoryDTO;
import com.example.blog_backend.model.dto.PostUserDTO;
import com.example.blog_backend.model.enums.PostStatus;
import lombok.Data;

import java.util.List;
import java.util.Set;


@Data
public class PostResponseDTO extends BaseDTO {
    private String title;
    private String content;
    private PostUserDTO user;
    private Set<CategoryDTO> categories;
    private List<String> tags;
    private int views;
    private int likes;
    private PostStatus status;
}
