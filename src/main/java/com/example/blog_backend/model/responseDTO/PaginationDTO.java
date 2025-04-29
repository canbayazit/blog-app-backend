package com.example.blog_backend.model.responseDTO;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class PaginationDTO {
    private int  totalPages;  // toplam sayfa sayısı
    private long totalElements;  // toplam eleman sayısı
    private int number;  // sayfa numarası
    private int size;  // bir sayfada kaç eleman olmalı
    private boolean hasContent;  // content var mı yok mu
    private boolean isLast; // sonuncu sayfada mıyız
    private Sort sort;  // sıralama
}
