package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import lombok.Data;

@Data
public class CommentLikeDTO extends BaseDTO {
    private CommentDTO comment;
    private UserDTO user;
    private ReactionTypeDTO reactionType;
}
