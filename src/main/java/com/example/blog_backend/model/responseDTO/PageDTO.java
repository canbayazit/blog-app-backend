package com.example.blog_backend.model.responseDTO;

import com.example.blog_backend.core.dto.BaseDTO;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
public class PageDTO<DTO extends BaseDTO> {
    private int  totalPages;  // toplam sayfa sayısı
    private long totalElements;  // toplam eleman sayısı
    private int number;  // sayfa numarası
    private int size;  // bir sayfada kaç eleman olmalı
    private List<DTO> content; // sayfadaki content
    private boolean hasContent;  // content var mı yok mu
    private boolean isLast; // sonuncu sayfada mıyız
    private Sort sort;  // sıralama
}
