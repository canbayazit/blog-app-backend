package com.example.blog_backend.core.service;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.model.requestDTO.BaseFilterRequestDTO;
import com.example.blog_backend.model.responseDTO.PageResponseDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface BaseCrudService<
        DTO extends BaseDTO,
        RequestDTO> {

    DTO save(RequestDTO requestDTO);

    List<DTO> getAll();

    DTO update(UUID uuid, RequestDTO requestDTO);

    DTO getByUUID(UUID uuid);

    Boolean deleteByUUID(UUID uuid);

    PageResponseDTO<DTO> getAllPageByFilter(BaseFilterRequestDTO filter);

}