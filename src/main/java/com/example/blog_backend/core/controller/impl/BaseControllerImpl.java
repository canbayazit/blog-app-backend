package com.example.blog_backend.core.controller.impl;

import com.example.blog_backend.core.controller.BaseController;
import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.core.service.BaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class BaseControllerImpl<
        DTO extends BaseDTO,
        RequestDTO,
        Service extends BaseService<DTO, RequestDTO>>
        implements BaseController<DTO, RequestDTO> {

    private final Service getService;

    @Override
    public ResponseEntity<List<DTO>> getAll() {
        return new ResponseEntity<>(getService.getAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DTO> save(@Valid @RequestBody RequestDTO requestDTO) {
        return new ResponseEntity<>(getService.save(requestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DTO> update(@PathVariable UUID uuid, @Valid @RequestBody RequestDTO requestDTO) {
        DTO dto = getService.update(uuid, requestDTO);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Boolean> deleteByUUID(@PathVariable UUID uuid) {
        Boolean isDeleted = getService.deleteByUUID(uuid);
        if (isDeleted) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<DTO> getByUUID(@PathVariable UUID uuid) {
        DTO dto = getService.getByUUID(uuid);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
