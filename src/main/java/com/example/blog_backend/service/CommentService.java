package com.example.blog_backend.service;

import com.example.blog_backend.core.service.BaseCrudService;
import com.example.blog_backend.entity.CommentEntity;
import com.example.blog_backend.model.requestDTO.CommentChildRequestDTO;
import com.example.blog_backend.model.requestDTO.CommentRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentResponseDTO;

import java.util.UUID;

public interface CommentService extends BaseCrudService<CommentEntity, CommentResponseDTO, CommentRequestDTO> {
    CommentResponseDTO createReply(UUID parentCommentUuid, CommentChildRequestDTO requestDTO);
}
