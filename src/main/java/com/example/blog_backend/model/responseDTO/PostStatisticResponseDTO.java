package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.dto.ReactionTypeCountDTO;
import lombok.Data;

import java.util.List;

@Data
public class PostStatisticResponseDTO extends BaseDTO {
    private boolean isLiked;
    private String reacted;
    private int likeCount;
    private int commentCount;
    private List<ReactionTypeCountDTO> reactionTypeCounts;
}
