package com.example.blog_backend.service;

import com.example.blog_backend.core.service.BaseService;
import com.example.blog_backend.model.requestDTO.CommentRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CommentService extends BaseService<CommentResponseDTO, CommentRequestDTO> {
    List<CommentResponseDTO> getAllCommentsByPostUuid(UUID postId);
}
