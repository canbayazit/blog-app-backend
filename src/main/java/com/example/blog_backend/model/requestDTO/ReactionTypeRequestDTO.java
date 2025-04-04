package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.dto.BaseRequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReactionTypeRequestDTO extends BaseRequestDTO {
    @NotBlank(message = "Reaction type name is mandatory")
    private String name;
}
