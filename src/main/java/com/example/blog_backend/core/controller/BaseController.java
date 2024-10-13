package com.example.blog_backend.core.controller;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.core.entity.BaseEntity;
import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.core.repository.BaseRepository;
import com.example.blog_backend.core.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public abstract class BaseController<
        Entity extends BaseEntity,
        DTO extends BaseDTO,
        RequestDto,
        Mapper extends IBaseMapper<DTO, Entity, RequestDto>,
        Repository extends BaseRepository<Entity>,
        Service extends BaseService<Entity, DTO, RequestDto, Mapper, Repository>> {   

    private final Service getService;

    @GetMapping("get-all")
    public ResponseEntity<List<DTO>> getAll() {
        return new ResponseEntity<>(getService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DTO> save(@RequestBody RequestDto requestDTO) {
        return new ResponseEntity<>(getService.save(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<DTO> update(@PathVariable UUID uuid, @RequestBody RequestDto requestDTO) {
        if (getService.update(uuid, requestDTO) != null) {
            return new ResponseEntity<>(getService.update(uuid, requestDTO), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Boolean> deleteByUUID(@PathVariable UUID uuid) {
        Boolean isDeleted = getService.deleteByUUID(uuid);
        if (isDeleted) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/{uuid}")
    public ResponseEntity<DTO> getByUUID(@PathVariable UUID uuid) {
        DTO dto = getService.getByUUID(uuid);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
