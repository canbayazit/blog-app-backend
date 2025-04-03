package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.enums.PostStatus;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
public class PostDTO extends BaseDTO {
    private String title;
    private String content;
    private UserSummaryDTO user;
    private PostStatus status;
    private Set<CategoryDTO> categories = new HashSet<>();
    private List<String> tags;
    private PostStatisticDTO statistics;
}
