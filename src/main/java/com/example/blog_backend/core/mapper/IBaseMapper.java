package com.example.blog_backend.core.mapper;

import com.example.blog_backend.core.dto.BaseDTO;
import com.example.blog_backend.core.entity.BaseEntity;
import com.example.blog_backend.model.responseDTO.PageDTO;
import com.example.blog_backend.model.responseDTO.PaginationDTO;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    Entity requestDTOToEntity(RequestDTO dto);

    List<Entity> requestDtoListTOEntityList(List<RequestDTO> dtoList);

    // entity'nin tamamını veya bir bölümünü update etmek için kullanılan mapper
    // request dtodan gelen entitylerde uuid ve id ortak alan olduğu için sanki yeni bir değer setliyormuş gibi algılıyor
    // o yüzden güncelleme yaparken uuid ve id 'yi güncellemeye çalışıyor mapstruct, bunu engellemek için ignore ediyoruz.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    Entity requestDtoToExistEntity(@MappingTarget Entity entity, RequestDTO requestDTO);

    // hasContent boolean türünde olduğu için mapstruct isContent olarak algılıyor yani "is" ifadesine bakıyormuş
    // "has" ifadesine bakmıyor mapsturct.
    @Mapping(target = "content", source = "entityPage.content")
    @Mapping(target = "pagination", source = "entityPage", qualifiedByName = "mapPageToPaginationDTO")
    PageDTO<DTO> pageEntityToPageDTO(Page<Entity> entityPage);

    @Named("mapPageToPaginationDTO")
    @Mapping(target = "hasContent", expression = "java(entityPage.hasContent())")
    @Mapping(target = "totalPages", source = "totalPages")
    @Mapping(target = "totalElements", source = "totalElements")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "last", source = "last")
    @Mapping(target = "sort", source = "sort")
    PaginationDTO mapPageToPaginationDTO(Page<Entity> entityPage);
}
