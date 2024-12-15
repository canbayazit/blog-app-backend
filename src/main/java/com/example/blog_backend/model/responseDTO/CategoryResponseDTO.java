package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.dto.CategoryPostDTO;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CategoryResponseDTO extends BaseDTO {
    private String name;
    private Set<CategoryPostDTO> posts = new HashSet<>();
}
