package com.example.blog_backend.service.impl;

import com.example.blog_backend.core.service.impl.BaseServiceImpl;
import com.example.blog_backend.entity.CategoryEntity;
import com.example.blog_backend.mapper.CategoryMapper;
import com.example.blog_backend.model.requestDTO.CategoryRequestDTO;
import com.example.blog_backend.model.responseDTO.CategoryResponseDTO;
import com.example.blog_backend.repository.CategoryRepository;
import com.example.blog_backend.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<
        CategoryEntity,
        CategoryResponseDTO,
        CategoryRequestDTO,
        CategoryMapper,
        CategoryRepository>
        implements CategoryService {
    public CategoryServiceImpl(CategoryMapper getMapper, CategoryRepository getRepository) {
        super(getMapper, getRepository);
    }
}
