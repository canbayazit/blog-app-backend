package com.example.blog_backend.service;

import com.example.blog_backend.core.service.BaseCrudService;
import com.example.blog_backend.entity.PostLikeEntity;
import com.example.blog_backend.model.requestDTO.PostLikeRequestDTO;
import com.example.blog_backend.model.responseDTO.PostLikeResponseDTO;


public interface PostLikeService extends BaseCrudService<PostLikeEntity, PostLikeResponseDTO, PostLikeRequestDTO> {
}
