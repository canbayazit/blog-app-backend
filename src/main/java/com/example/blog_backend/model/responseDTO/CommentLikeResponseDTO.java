package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.dto.UserSummaryDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentLikeResponseDTO extends BaseDTO {
    private String reactionType;
    private UserSummaryDTO user;
    private UUID commentId;
}
