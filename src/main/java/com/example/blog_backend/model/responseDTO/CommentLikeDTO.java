package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentLikeDTO extends BaseDTO {
    private UUID commentId;
    private UserSummaryDTO user;
    private ReactionTypeDTO reactionType;
}
