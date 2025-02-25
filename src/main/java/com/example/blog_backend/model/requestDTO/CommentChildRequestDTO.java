package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CommentChildRequestDTO extends BaseRequestDTO {
    @NotBlank(message = "comment is mandatory")
    @Size(max = 500, message = "Comment cannot exceed 500 characters.")
    private String comment;
}
