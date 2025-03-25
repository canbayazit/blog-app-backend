package com.example.blog_backend.service;

import com.example.blog_backend.core.service.BaseCrudService;
import com.example.blog_backend.entity.FavoriteEntity;
import com.example.blog_backend.model.requestDTO.FavoriteRequestDTO;
import com.example.blog_backend.model.responseDTO.FavoriteDTO;

public interface FavoriteService extends BaseCrudService<FavoriteEntity, FavoriteDTO, FavoriteRequestDTO> {
}
