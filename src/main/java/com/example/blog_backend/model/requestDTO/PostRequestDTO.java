package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import com.example.blog_backend.model.enums.PostStatus;
import lombok.Data;

import java.util.*;

@Data
public class PostRequestDTO extends BaseRequestDTO {
    private String title;
    private String content;
    private UserRequestDTO user;
    private PostStatus status;
    private Set<CategoryRequestDTO> categories;
    private List<CommentRequestDTO> comments;
    private List<String> tags;
    private PostStatisticRequestDTO statistics;
}
