package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.CommentAggregateEntity;
import com.example.blog_backend.model.requestDTO.CommentAggregateRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentAggregateDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CommentAggregateMapper extends IBaseMapper<
        CommentAggregateDTO,
        CommentAggregateEntity,
        CommentAggregateRequestDTO> {

    @Override
    @BeanMapping(ignoreByDefault = true, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    CommentAggregateEntity requestDTOToEntity(CommentAggregateRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "comment", ignore = true)
    CommentAggregateEntity requestDtoToExistEntity(@MappingTarget CommentAggregateEntity entity,
                                                                   CommentAggregateRequestDTO requestDTO);

    @Mapping(target = "commentId", source = "comment.uuid")
    CommentAggregateDTO entityToDTO(CommentAggregateEntity entity);
}
