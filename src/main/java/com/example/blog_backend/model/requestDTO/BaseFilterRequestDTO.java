package com.example.blog_backend.model.requestDTO;

import com.example.blog_backend.core.specification.SearchCriteria;
import com.example.blog_backend.model.dto.SortDTO;
import lombok.Data;

import java.util.List;

@Data
public class BaseFilterRequestDTO {
    private int pageNumber; // sayfa numarası
    private int pageSize;  // bir sayfada kaç element olmalı
    private SortDTO sortDTO;  // verileri neye göre sıralıcaz
    private List<SearchCriteria> filters;  // filtreleme için kullanılacak kriterler
}
