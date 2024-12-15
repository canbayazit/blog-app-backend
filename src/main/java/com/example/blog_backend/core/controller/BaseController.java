package com.example.blog_backend.core.controller;

import com.example.blog_backend.core.dto.BaseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface BaseController<
        DTO extends BaseDTO,
        RequestDTO> {

    @GetMapping("get-all")
    ResponseEntity<List<DTO>> getAll();

    @PostMapping("/create")
    ResponseEntity<DTO> save(@RequestBody RequestDTO requestDTO);

    @PutMapping("/update/{uuid}")
    ResponseEntity<DTO> update(@PathVariable UUID uuid, @RequestBody RequestDTO requestDTO);

    @DeleteMapping("/delete/{uuid}")
    ResponseEntity<Boolean> deleteByUUID(@PathVariable UUID uuid);

    @GetMapping("/get/{uuid}")
    ResponseEntity<DTO> getByUUID(@PathVariable UUID uuid);
}
