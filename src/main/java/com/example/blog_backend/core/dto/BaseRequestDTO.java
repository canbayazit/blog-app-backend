package com.example.blog_backend.core.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BaseRequestDTO {
    private Long id;
    private UUID uuid;
}
