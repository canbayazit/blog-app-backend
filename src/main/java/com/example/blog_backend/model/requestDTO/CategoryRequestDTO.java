package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDTO extends BaseRequestDTO {
    @NotBlank(message = "Category name is mandatory")
    private String name;
}
