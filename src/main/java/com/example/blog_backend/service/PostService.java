package com.example.blog_backend.service;

import com.example.blog_backend.core.service.BaseCrudService;
import com.example.blog_backend.model.requestDTO.PostRequestDTO;
import com.example.blog_backend.model.requestDTO.PostStatusRequestDTO;
import com.example.blog_backend.model.responseDTO.PostResponseDTO;

import java.util.List;
import java.util.UUID;


public interface PostService extends BaseCrudService<PostResponseDTO, PostRequestDTO> {
    List<PostResponseDTO> getMyPostsByStatus(PostStatusRequestDTO status);
    PostResponseDTO sendPublishRequest(UUID postId);
}
