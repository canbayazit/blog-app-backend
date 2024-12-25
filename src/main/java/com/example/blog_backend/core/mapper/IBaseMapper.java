package com.example.blog_backend.core.mapper;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.core.entity.BaseEntity;
import com.example.blog_backend.model.responseDTO.PageResponseDTO;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

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

    // hasContent boolean türünde olduğu için mapstruct isContent olarak algılıyor yani "is" ifadesine bakıyormuş
    // "has" ifadesine bakmıyor mapsturct.
    @Mapping(target = "hasContent", expression = "java(entityPage.hasContent())")
    PageResponseDTO<DTO> pageEntityToPageDTO(Page<Entity> entityPage);
}
