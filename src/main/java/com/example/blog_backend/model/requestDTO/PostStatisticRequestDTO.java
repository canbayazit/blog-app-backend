package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import lombok.Data;

@Data
public class PostStatisticRequestDTO extends BaseRequestDTO {
    private PostRequestDTO post;
    private int viewCount = 0;
    private int likeCount = 0;
    private int commentCount = 0;
}
