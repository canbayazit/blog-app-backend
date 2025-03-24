package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import lombok.Data;

@Data
public class CategoryBasicDTO extends BaseDTO {
    private String name;
}
