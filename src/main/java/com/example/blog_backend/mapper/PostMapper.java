package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.PostEntity;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.model.requestDTO.PostRequestDTO;
import com.example.blog_backend.model.responseDTO.PageDTO;
import com.example.blog_backend.model.responseDTO.PostDTO;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UserMapper.class, CategoryMapper.class, PostStatisticMapper.class, CommentMapper.class})
public interface PostMapper extends IBaseMapper<PostDTO, PostEntity, PostRequestDTO> {

    @Override
    default PostEntity requestDTOToEntity(PostRequestDTO requestDTO){
        return null;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "user", expression = "java(userEntity)")
    PostEntity requestDTOToEntity(PostRequestDTO requestDTO, @Context UserEntity userEntity);

    // tek bir mapper methoduna AfterMapping uygulamak istiyorsak o methodun parametre imzası AfterMapping methodunun
    // parametre imzasıyla aynı olmalı. Aşağıda görüldüğü gibi requestDTOToEntity için parametreler aynı olacak
    // şekilde yazdık ve sadece requestDTOToEntity için uygulanmış oldu. Yazmasaydık sadece
    // @MappingTarget PostEntity postEntity parametre olarak gönderseydik bu sefer post mapper'daki PostEntity dönen
    // metotlara uygulanmış olacaktı.
    @AfterMapping
    default void linkStatistic(
            PostRequestDTO requestDTO,
            @Context UserEntity userEntity,
            @MappingTarget PostEntity postEntity) {
        // Sadece "requestDTOToEntity(PostRequestDTO, UserEntity)->PostEntity" metodundan sonra devreye girer.
        if (postEntity.getStatistics() != null) {
            postEntity.getStatistics().setPost(postEntity);
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "statistics", ignore = true)
    PostEntity requestDtoToExistEntity(@MappingTarget PostEntity entity, PostRequestDTO requestDTO);

    @Override
    @Mapping(target = "pagination", source = "entityPage", qualifiedByName = "mapPageToPaginationDTO")
    @Mapping(target = "content", source = "entityPage.content", qualifiedByName = "toDtoListWithoutContent")
    PageDTO<PostDTO> pageEntityToPageDTO(Page<PostEntity> entityPage);

    @Named("toDtoListWithoutContent")
    @IterableMapping(qualifiedByName = "toDtoWithoutContent") // koleksiyon dönüşümlerinde ve null stratejilerinde kullanılır
    List<PostDTO> toDtoListWithoutContent(List<PostEntity> entityList);

    @Named("toDtoWithoutContent")
    @Mapping(target = "content", ignore = true)
    PostDTO toDtoWithoutContent(PostEntity entity);
}
