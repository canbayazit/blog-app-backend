package com.example.blog_backend.model.dto;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class SortDTO {
    private String columnName; // kolon isimlerine göre sıralama
    private Sort.Direction direction; // asc veya desc sıralama
}
