package com.example.blog_backend.core.specification;

import lombok.Data;

@Data
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}
