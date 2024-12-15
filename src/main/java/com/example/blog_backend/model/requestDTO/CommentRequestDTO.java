package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentRequestDTO {
    @NotBlank(message = "comment is mandatory")
    @Size(max = 500, message = "Comment cannot exceed 500 characters.")
    private String comment;

    @NotNull(message = "Post ID is mandatory")
    private UUID postId;
}
