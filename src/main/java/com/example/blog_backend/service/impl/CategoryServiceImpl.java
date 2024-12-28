package com.example.blog_backend.service.impl;

import com.example.blog_backend.core.service.impl.AbstractBaseCrudServiceImpl;
import com.example.blog_backend.entity.CategoryEntity;
import com.example.blog_backend.mapper.CategoryMapper;
import com.example.blog_backend.model.requestDTO.CategoryRequestDTO;
import com.example.blog_backend.model.responseDTO.CategoryResponseDTO;
import com.example.blog_backend.repository.CategoryRepository;
import com.example.blog_backend.service.CategoryService;
import com.example.blog_backend.specification.CategorySpecification;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CategoryServiceImpl extends AbstractBaseCrudServiceImpl<
        CategoryEntity,
        CategoryResponseDTO,
        CategoryRequestDTO,
        CategoryMapper,
        CategoryRepository,
        CategorySpecification>
        implements CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryMapper getMapper, CategoryRepository getRepository,
                               CategorySpecification categorySpecification) {
        super(getMapper, getRepository, categorySpecification);
        this.categoryRepository = getRepository;
    }
    @Override
    public long countCategoryByUuids(Set<UUID> uuids) {
        return categoryRepository.countByUuidIn(uuids);
    }
}
