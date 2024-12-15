package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDTO extends BaseDTO {
    @NotBlank(message = "Category is mandatory")
    private String name;
}
