package com.example.blog_backend.model.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class PostRequestDTO {
    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Content is mandatory")
    private String content;

    @NotEmpty(message = "category is mandatory")
    private Set<UUID> categoryIds;

    private List<String> tags;
}
