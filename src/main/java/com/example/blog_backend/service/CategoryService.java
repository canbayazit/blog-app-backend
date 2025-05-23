package com.example.blog_backend.service;

import com.example.blog_backend.core.service.BaseCrudService;
import com.example.blog_backend.entity.CategoryEntity;
import com.example.blog_backend.model.requestDTO.CategoryRequestDTO;
import com.example.blog_backend.model.responseDTO.CategoryDTO;

import java.util.Set;
import java.util.UUID;

public interface CategoryService extends BaseCrudService<CategoryEntity, CategoryDTO, CategoryRequestDTO> {
    long countCategoryByUuids(Set<UUID> uuids);
}
