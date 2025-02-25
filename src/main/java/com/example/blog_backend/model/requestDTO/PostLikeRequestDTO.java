package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PostLikeRequestDTO extends BaseRequestDTO {
    @NotNull(message = "Post ID is mandatory")
    private UUID postId;

    @NotNull(message = "Reaction type is mandatory")
    private String reactionType;
}
