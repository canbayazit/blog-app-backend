package com.example.blog_backend.core.controller.impl;

import com.example.blog_backend.core.controller.BaseCrudController;
import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.core.entity.BaseEntity;
import com.example.blog_backend.core.service.BaseCrudService;
import com.example.blog_backend.model.requestDTO.BaseFilterRequestDTO;
import com.example.blog_backend.model.responseDTO.ApiResponseDTO;
import com.example.blog_backend.model.responseDTO.PageDTO;
import com.example.blog_backend.util.response.ApiResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class AbstractBaseCrudControllerImpl<
        Entity extends BaseEntity,
        DTO extends BaseDTO,
        RequestDTO,
        Service extends BaseCrudService<Entity, DTO, RequestDTO>>
        implements BaseCrudController<DTO, RequestDTO> {

    private final Service getService;

    @Override
    public ResponseEntity<ApiResponseDTO<List<DTO>>> getAll() {
        List<DTO> data = getService.getAll();
        ApiResponseDTO<List<DTO>> response = ApiResponseUtil.success(data, "Data retrieved successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponseDTO<PageDTO<DTO>>> getAllPageByFilter(@RequestBody BaseFilterRequestDTO filterRequestDTO) {
        PageDTO<DTO> data = getService.getAllPageByFilter(filterRequestDTO);
        ApiResponseDTO<PageDTO<DTO>> response = ApiResponseUtil.success(data, "Data retrieved successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponseDTO<DTO>> save(@Valid @RequestBody RequestDTO requestDTO) {
        DTO data = getService.save(requestDTO);
        ApiResponseDTO<DTO> response = ApiResponseUtil.success(data, "Data created successfully.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponseDTO<DTO>> update(@PathVariable UUID uuid, @Valid @RequestBody RequestDTO requestDTO) {
        DTO data = getService.update(uuid, requestDTO);
        if (data != null) {
            ApiResponseDTO<DTO> response = ApiResponseUtil.success(data, "Update successful.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponseDTO<DTO> response = ApiResponseUtil.error("An error occurred.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ApiResponseDTO<Boolean>> deleteByUUID(@PathVariable UUID uuid) {
        Boolean isDeleted = getService.deleteByUUID(uuid);
        if (isDeleted) {
            ApiResponseDTO<Boolean> response = ApiResponseUtil.success(true, "Deletion successful.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponseDTO<Boolean> response = ApiResponseUtil.error("Deletion failed.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ApiResponseDTO<DTO>> getByUUID(@PathVariable UUID uuid) {
        DTO data = getService.getByUUID(uuid);
        if (data != null) {
            ApiResponseDTO<DTO> response = ApiResponseUtil.success(data, "Data retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponseDTO<DTO> response = ApiResponseUtil.error("Data not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
