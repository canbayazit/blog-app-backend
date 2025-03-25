package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.*;
import com.example.blog_backend.model.requestDTO.FavoriteRequestDTO;
import com.example.blog_backend.model.responseDTO.FavoriteDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UserMapper.class, PostMapper.class})
public interface FavoriteMapper extends IBaseMapper<FavoriteDTO, FavoriteEntity, FavoriteRequestDTO> {
    @Mapping(target = "user", expression = "java(userEntity)")
    @Mapping(target = "post", expression = "java(postEntity)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    FavoriteEntity requestDTOToEntity(FavoriteRequestDTO requestDTO,
                                      @Context PostEntity postEntity,
                                      @Context UserEntity userEntity);

    @Override
    default FavoriteEntity requestDTOToEntity(FavoriteRequestDTO dto) {
        return null;
    }

    @Override
    default FavoriteEntity requestDtoToExistEntity(@MappingTarget FavoriteEntity entity, FavoriteRequestDTO requestDTO) {
        return null;
    }
}
