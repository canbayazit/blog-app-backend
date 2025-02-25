package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.dto.UserSummaryDTO;
import lombok.Data;

@Data
public class CommentResponseDTO extends BaseDTO {
    private String comment;
    private UserSummaryDTO user;
    private CommentAggregateResponseDTO statistics;
    private String repliedTo;
}
