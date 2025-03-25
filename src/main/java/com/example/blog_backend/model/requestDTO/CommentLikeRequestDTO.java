package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import com.example.blog_backend.entity.CommentEntity;
import com.example.blog_backend.entity.ReactionTypeEntity;
import com.example.blog_backend.entity.UserEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentLikeRequestDTO extends BaseRequestDTO {
    private CommentRequestDTO comment;
    private UserRequestDTO user;
    private ReactionTypeRequestDTO reactionType;

    /*@NotNull(message = "Comment ID is mandatory")
    private UUID commentId;

    @NotNull(message = "Reaction type is mandatory")
    private String reactionType;*/
}
