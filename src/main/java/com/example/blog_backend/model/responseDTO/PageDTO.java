package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class PageDTO<DTO extends BaseDTO> {
    private List<DTO> content;
    private PaginationDTO pagination;
}
