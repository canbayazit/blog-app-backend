package com.example.blog_backend.core.service;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.core.entity.BaseEntity;
import com.example.blog_backend.model.requestDTO.BaseFilterRequestDTO;
import com.example.blog_backend.model.responseDTO.PageDTO;

import java.util.List;
import java.util.UUID;

public interface BaseCrudService<
        Entity extends BaseEntity,
        DTO extends BaseDTO,
        RequestDTO> {

    DTO save(RequestDTO requestDTO);

    List<DTO> getAll();

    DTO update(UUID uuid, RequestDTO requestDTO);

    DTO getByUUID(UUID uuid);

    Boolean deleteByUUID(UUID uuid);

    PageDTO<DTO> getAllPageByFilter(BaseFilterRequestDTO filter);

}