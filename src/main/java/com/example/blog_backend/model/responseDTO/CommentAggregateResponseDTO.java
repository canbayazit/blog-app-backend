package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.dto.ReactionTypeCountDTO;
import lombok.Data;

import java.util.List;

@Data
public class CommentAggregateResponseDTO extends BaseDTO {
    private boolean isLiked;
    private String reacted;
    private int likeCount;
    private List<ReactionTypeCountDTO> reactionTypeCounts;
}
