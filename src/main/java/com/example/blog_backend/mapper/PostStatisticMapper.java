package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.*;
import com.example.blog_backend.model.requestDTO.*;
import com.example.blog_backend.model.responseDTO.PostStatisticDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PostStatisticMapper extends IBaseMapper<PostStatisticDTO, PostStatisticEntity, PostStatisticRequestDTO> {
    @Override
    @BeanMapping(ignoreByDefault = true, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    PostStatisticEntity requestDTOToEntity(PostStatisticRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "post", ignore = true)
    PostStatisticEntity requestDtoToExistEntity(@MappingTarget PostStatisticEntity entity, PostStatisticRequestDTO requestDTO);

    @Mapping(target = "postId", source = "post.uuid")
    PostStatisticDTO entityToDTO(PostStatisticEntity entity);
}
