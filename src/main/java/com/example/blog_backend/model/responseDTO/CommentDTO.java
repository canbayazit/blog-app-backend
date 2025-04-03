package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentDTO extends BaseDTO {
    private String comment;
    private UserSummaryDTO user;
    private UUID postId;
    private UUID parentCommentId;
    private CommentAggregateDTO statistics;
}
