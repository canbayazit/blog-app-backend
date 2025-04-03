package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentLikeRequestDTO extends BaseRequestDTO {
    @NotNull(message = "Comment ID is mandatory")
    private UUID commentId;
    private ReactionTypeRequestDTO reactionType;
}
