package com.example.blog_backend.service;

import com.example.blog_backend.core.service.BaseCrudService;
import com.example.blog_backend.entity.PostStatisticEntity;
import com.example.blog_backend.model.requestDTO.PostStatisticRequestDTO;
import com.example.blog_backend.model.responseDTO.PostStatisticResponseDTO;

public interface PostStatisticService extends BaseCrudService<PostStatisticEntity, PostStatisticResponseDTO, PostStatisticRequestDTO> {
    void incrementPostCommentCount(Long postId, int inc);
    void decrementPostCommentCount(Long postId, int dec);
    void incrementPostLikeCount(Long postId, int inc);
    void decrementPostLikeCount(Long postId, int dec);
}
