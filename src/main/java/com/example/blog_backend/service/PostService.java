package com.example.blog_backend.service;

import com.example.blog_backend.core.service.BaseService;
import com.example.blog_backend.model.requestDTO.PostRequestDTO;
import com.example.blog_backend.model.responseDTO.PostResponseDTO;


public interface PostService extends BaseService<PostResponseDTO, PostRequestDTO> {
}
