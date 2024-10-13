package com.example.blog_backend.core.service;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.core.entity.BaseEntity;
import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.core.repository.BaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public abstract class BaseService<
        Entity extends BaseEntity,
        DTO extends BaseDTO,
        RequestDTO,
        Mapper extends IBaseMapper<DTO, Entity, RequestDTO>,
        Repository extends BaseRepository<Entity>
        > {

    private final Mapper getMapper;

    private final Repository getRepository;

    @Transactional
    public DTO save(RequestDTO requestDTO) {
        Entity entity = getMapper.requestDTOToEntity(requestDTO);
        getRepository.save(entity);
        return getMapper.entityToDTO(entity);
    }

    public List<DTO> getAll() {
        return getMapper.entityListToDTOList(getRepository.findAll());
    }

    @Transactional
    public DTO update(UUID uuid, RequestDTO requestDTO) {
        Entity entity = getRepository.findByUuid(uuid).orElse(null);
        if (entity != null) {
            entity = getMapper.requestDtoToExistEntity(entity, requestDTO);
            getRepository.save(entity);
            return getMapper.entityToDTO(entity);
        } else {
            return null;
        }
    }

    public DTO getByUUID(UUID uuid) {
        Entity entity = getRepository.findByUuid(uuid).orElse(null);
        if (entity != null) {
            return getMapper.entityToDTO(entity);
        } else {
            return null;
        }
    }

    @Transactional
    public Boolean deleteByUUID(UUID uuid) {
        Entity entity = getRepository.findByUuid(uuid).orElse(null);
        if (entity != null) {
            getRepository.delete(entity);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
