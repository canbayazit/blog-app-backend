package com.example.blog_backend.core.controller.impl;

import com.example.blog_backend.core.controller.BaseCrudController;
import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.core.entity.BaseEntity;
import com.example.blog_backend.core.service.BaseCrudService;
import com.example.blog_backend.model.requestDTO.BaseFilterRequestDTO;
import com.example.blog_backend.model.responseDTO.PageDTO;
import com.example.blog_backend.util.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<DTO>>> getAll() {
        List<DTO> data = getService.getAll();
        ApiResponse<List<DTO>> response = ApiResponse.success(data, "Data retrieved successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<PageDTO<DTO>>> getAllPageByFilter(@RequestBody BaseFilterRequestDTO filterRequestDTO) {
        PageDTO<DTO> data = getService.getAllPageByFilter(filterRequestDTO);
        ApiResponse<PageDTO<DTO>> response = ApiResponse.success(data, "Data retrieved successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<DTO>> save(@Valid @RequestBody RequestDTO requestDTO) {
        DTO data = getService.save(requestDTO);
        ApiResponse<DTO> response = ApiResponse.success(data, "Data created successfully.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse<DTO>> update(@PathVariable UUID uuid, @Valid @RequestBody RequestDTO requestDTO) {
        DTO data = getService.update(uuid, requestDTO);
        if (data != null) {
            ApiResponse<DTO> response = ApiResponse.success(data, "Update successful.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<DTO> response = ApiResponse.error(HttpStatus.NOT_FOUND, "An error occurred.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> deleteByUUID(@PathVariable UUID uuid) {
        Boolean isDeleted = getService.deleteByUUID(uuid);
        if (isDeleted) {
            ApiResponse<Boolean> response = ApiResponse.success(true, "Deletion successful.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Boolean> response = ApiResponse.error(HttpStatus.NOT_FOUND, "Deletion failed.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<DTO>> getByUUID(@PathVariable UUID uuid) {
        DTO data = getService.getByUUID(uuid);
        if (data != null) {
            ApiResponse<DTO> response = ApiResponse.success(data, "Data retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<DTO> response = ApiResponse.error(HttpStatus.NOT_FOUND, "Data not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
