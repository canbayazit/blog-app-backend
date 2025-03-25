package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDTO extends BaseDTO {
    private String comment;
    private UserDTO user;
    private PostDTO post;
    private CommentDTO parentComment;
    private CommentDTO repliedTo;
    private List<CommentDTO> replies = new ArrayList<>();
    private CommentAggregateDTO statistics;
}
