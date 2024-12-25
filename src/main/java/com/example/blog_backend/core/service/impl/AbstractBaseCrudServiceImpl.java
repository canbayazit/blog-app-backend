package com.example.blog_backend.core.service.impl;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.core.entity.BaseEntity;
import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.core.repository.BaseRepository;
import com.example.blog_backend.core.service.BaseCrudService;
import com.example.blog_backend.model.requestDTO.BaseFilterRequestDTO;
import com.example.blog_backend.model.responseDTO.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public abstract class AbstractBaseCrudServiceImpl<
        Entity extends BaseEntity,
        DTO extends BaseDTO,
        RequestDTO,
        Mapper extends IBaseMapper<DTO, Entity, RequestDTO>,
        Repository extends BaseRepository<Entity>>
        implements BaseCrudService<DTO,RequestDTO> {

    private final Mapper getMapper;

    private final Repository getRepository;

    @Override
    public List<DTO> getAll() {
        List<Entity> entities = getRepository.findAll();
        return getMapper.entityListToDTOList(entities);
    }

    // verileri pagination ile getiriyoruz.
    // sadece pagination yapacaksak parametreleri url'den request param ile almak mantıklıdır.

    // Ama sadece pagination değilde filtrelemeler de yapacaksak o zaman dto üzerinden almak daha iyi bir çözümdür
    // filter dto oluşturduk çünkü pagination dışında filtreleme de yapıyoruz
    @Override
    public PageResponseDTO<DTO> getAllPageByFilter(BaseFilterRequestDTO filter) {
        Pageable pageable;
        if (filter.getSortDTO() != null) {
            if (filter.getSortDTO().getDirection() == Sort.Direction.DESC) {
                pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize(),
                        Sort.by(filter.getSortDTO().getColumnName()).descending());
            } else {
                pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize(),
                        Sort.by(filter.getSortDTO().getColumnName()).ascending());
            }
        } else {
            pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize(),
                    Sort.by("id").ascending());
        }

        Page<Entity> entityPage = getRepository.findAll(pageable);
        return getMapper.pageEntityToPageDTO(entityPage);
    }

    @Transactional
    @Override
    public DTO save(RequestDTO requestDTO) {
        Entity entity = getMapper.requestDTOToEntity(requestDTO);
        getRepository.save(entity);
        return getMapper.entityToDTO(entity);
    }

    @Transactional
    @Override
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

    @Override
    public DTO getByUUID(UUID uuid) {
        Entity entity = getRepository.findByUuid(uuid).orElse(null);
        if (entity != null) {
            return getMapper.entityToDTO(entity);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
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
