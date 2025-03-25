package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentRequestDTO extends BaseRequestDTO {
    private String comment;
    private UserRequestDTO user;
    private PostRequestDTO post;
    private CommentRequestDTO parentComment;
    private CommentRequestDTO repliedTo;
    private List<CommentRequestDTO> replies = new ArrayList<>();
    private CommentAggregateRequestDTO statistics;
}
