package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.CategoryEntity;
import com.example.blog_backend.model.requestDTO.CategoryRequestDTO;
import com.example.blog_backend.model.responseDTO.CategoryDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends IBaseMapper<CategoryDTO, CategoryEntity, CategoryRequestDTO> {
}