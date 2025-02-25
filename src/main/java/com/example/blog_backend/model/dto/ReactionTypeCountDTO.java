package com.example.blog_backend.model.dto;

import com.example.blog_backend.core.dto.BaseDTO;
import lombok.Data;

@Data
public class ReactionTypeCountDTO extends BaseDTO {
    private String reactionType;
    private long count;

}
