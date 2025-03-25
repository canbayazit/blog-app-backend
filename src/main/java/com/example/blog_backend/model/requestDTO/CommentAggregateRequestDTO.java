package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import lombok.Data;

@Data
public class CommentAggregateRequestDTO extends BaseRequestDTO {
    private CommentRequestDTO comment;
    private int likeCount = 0;
    private int childCount;
}
