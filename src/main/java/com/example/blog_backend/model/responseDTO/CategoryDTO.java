package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.entity.PostEntity;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
public class CategoryDTO extends BaseDTO {
    private String name;
}
