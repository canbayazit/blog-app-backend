package com.example.blog_backend.service;

import com.example.blog_backend.core.service.BaseCrudService;
import com.example.blog_backend.entity.PostEntity;
import com.example.blog_backend.model.requestDTO.PostRequestDTO;
import com.example.blog_backend.model.requestDTO.PostStatusRequestDTO;
import com.example.blog_backend.model.responseDTO.PostDTO;

import java.util.List;
import java.util.UUID;


public interface PostService extends BaseCrudService<PostEntity, PostDTO, PostRequestDTO> {
    List<PostDTO> getMyPostsByStatus(PostStatusRequestDTO status);
    PostDTO sendPublishRequest(UUID postId);
}
