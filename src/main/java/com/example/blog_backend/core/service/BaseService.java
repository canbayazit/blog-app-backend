package com.example.blog_backend.core.service;

import com.example.blog_backend.core.dto.BaseDTO;

import java.util.List;
import java.util.UUID;

public interface BaseService<
        DTO extends BaseDTO,
        RequestDTO> {

    DTO save(RequestDTO requestDTO);

    List<DTO> getAll();

    DTO update(UUID uuid, RequestDTO requestDTO);

    DTO getByUUID(UUID uuid);

    Boolean deleteByUUID(UUID uuid);

}