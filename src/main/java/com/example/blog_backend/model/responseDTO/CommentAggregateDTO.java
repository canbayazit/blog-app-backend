package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.dto.ReactionTypeCountDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CommentAggregateDTO extends BaseDTO {
    private boolean isLiked;
    private String reacted;
    private UUID commentId;
    private int likeCount;
    private int childCount;
    private List<ReactionTypeCountDTO> reactionTypeCounts;
}
