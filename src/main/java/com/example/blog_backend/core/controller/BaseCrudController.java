package com.example.blog_backend.core.controller;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.requestDTO.BaseFilterRequestDTO;
import com.example.blog_backend.model.responseDTO.ApiResponseDTO;
import com.example.blog_backend.model.responseDTO.PageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface BaseCrudController<
        DTO extends BaseDTO,
        RequestDTO> {

    @GetMapping("get-all")
    ResponseEntity<ApiResponseDTO<List<DTO>>> getAll();

    @PostMapping("get-all-page-by-filter")
    ResponseEntity<ApiResponseDTO<PageDTO<DTO>>> getAllPageByFilter(BaseFilterRequestDTO filterRequestDTO);

    @PostMapping("/create")
    ResponseEntity<ApiResponseDTO<DTO>> save(@RequestBody RequestDTO requestDTO);

    @PutMapping("/update/{uuid}")
    ResponseEntity<ApiResponseDTO<DTO>> update(@PathVariable UUID uuid, @RequestBody RequestDTO requestDTO);

    @DeleteMapping("/delete/{uuid}")
    ResponseEntity<ApiResponseDTO<Boolean>> deleteByUUID(@PathVariable UUID uuid);

    @GetMapping("/get/{uuid}")
    ResponseEntity<ApiResponseDTO<DTO>> getByUUID(@PathVariable UUID uuid);
}
