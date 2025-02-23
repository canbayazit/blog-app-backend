package com.example.blog_backend.controller;

import com.example.blog_backend.core.controller.impl.AbstractBaseCrudControllerImpl;
import com.example.blog_backend.entity.CategoryEntity;
import com.example.blog_backend.model.requestDTO.CategoryRequestDTO;
import com.example.blog_backend.model.responseDTO.CategoryResponseDTO;
import com.example.blog_backend.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/category")
public class CategoryController extends AbstractBaseCrudControllerImpl<
        CategoryEntity,
        CategoryResponseDTO,
        CategoryRequestDTO,
        CategoryService> {
    public CategoryController(CategoryService getService) {
        super(getService);
    }
}
