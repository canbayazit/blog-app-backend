package com.example.blog_backend.service;

import com.example.blog_backend.core.service.BaseCrudService;
import com.example.blog_backend.entity.CommentLikeEntity;
import com.example.blog_backend.model.requestDTO.CommentLikeRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentLikeResponseDTO;


public interface CommentLikeService extends BaseCrudService<CommentLikeEntity, CommentLikeResponseDTO, CommentLikeRequestDTO> {
}
