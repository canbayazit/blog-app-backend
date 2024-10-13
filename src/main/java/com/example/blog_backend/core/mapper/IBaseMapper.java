package com.example.blog_backend.core.mapper;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.core.entity.BaseEntity;
import org.mapstruct.MappingTarget;

import java.util.List;

public interface IBaseMapper<
        DTO extends BaseDTO,
        Entity extends BaseEntity,
        RequestDTO> {
    DTO entityToDTO(Entity entity);

    Entity dtoToEntity(DTO dto);

    List<DTO> entityListToDTOList(List<Entity> entityList);

    List<Entity> dtoListTOEntityList(List<DTO> dtoList);

    Entity requestDTOToEntity(RequestDTO dto);

    List<Entity> requestDtoListTOEntityList(List<RequestDTO> dtoList);

    //entity'nin tamamını veya bir bölümünü update etmek için kullanılan mapper
    Entity requestDtoToExistEntity(@MappingTarget Entity entity, RequestDTO requestDTO);
}
