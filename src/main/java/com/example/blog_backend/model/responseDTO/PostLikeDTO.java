package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class PostLikeDTO extends BaseDTO {
    private UUID postId;
    private UserSummaryDTO user;
    private ReactionTypeDTO reactionType;
}
