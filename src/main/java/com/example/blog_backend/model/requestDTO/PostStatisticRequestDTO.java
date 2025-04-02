package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class PostStatisticRequestDTO extends BaseRequestDTO {
    private UUID postId;
    private int viewCount;
    private int likeCount;
    private int commentCount;
}
