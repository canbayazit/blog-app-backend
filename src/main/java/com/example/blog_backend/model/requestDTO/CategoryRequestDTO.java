package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDTO extends BaseRequestDTO {
    @NotBlank(message = "Category name is mandatory")
    @Size(max = 50, message = "Category cannot exceed 50 characters.")
    private String name;
}
