package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class FavoriteRequestDTO extends BaseRequestDTO {
    @NotNull(message = "Post ID is mandatory")
    private UUID postId;
}
