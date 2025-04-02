package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentAggregateRequestDTO extends BaseRequestDTO {
    private UUID commentId;
    private int likeCount;
    private int childCount;
}
