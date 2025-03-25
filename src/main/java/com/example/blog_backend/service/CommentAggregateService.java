package com.example.blog_backend.service;

import com.example.blog_backend.core.service.BaseCrudService;
import com.example.blog_backend.entity.CommentAggregateEntity;
import com.example.blog_backend.model.requestDTO.CommentAggregateRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentAggregateDTO;

public interface CommentAggregateService extends BaseCrudService<CommentAggregateEntity, CommentAggregateDTO,
        CommentAggregateRequestDTO> {
    void incrementCommentLikeCount(Long commentId, int inc);
    void decrementCommentLikeCount(Long commentId, int dec);
    void incrementChildCount(Long commentId, int inc);
    void decrementChildCount(Long commentId, int dec);
}
