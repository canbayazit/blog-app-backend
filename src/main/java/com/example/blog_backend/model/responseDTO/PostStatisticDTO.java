package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.dto.ReactionTypeCountDTO;
import lombok.Data;

import java.util.List;

@Data
public class PostStatisticDTO extends BaseDTO {
    private boolean isLiked;
    private String reacted;
    private PostDTO post;
    private int likeCount;
    private int viewCount;
    private int commentCount;
    private List<ReactionTypeCountDTO> reactionTypeCounts;
}
