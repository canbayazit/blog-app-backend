package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.*;

@Data
public class PostRequestDTO extends BaseRequestDTO {
    @NotBlank(message = "Title is mandatory")
    @Size(max = 50, message = "Title cannot exceed 50 characters.")
    private String title;
    @Size(max = 5000, message = "Content cannot exceed 5000 characters.")
    private String content;
    private Set<CategoryRequestDTO> categories;
    private List<String> tags;
    private PostStatisticRequestDTO statistics;
}
