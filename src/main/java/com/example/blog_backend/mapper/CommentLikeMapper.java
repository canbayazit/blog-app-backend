package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.*;
import com.example.blog_backend.model.requestDTO.CommentLikeRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentLikeResponseDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface CommentLikeMapper extends IBaseMapper<CommentLikeResponseDTO, CommentLikeEntity, CommentLikeRequestDTO> {

    @Mapping(target = "user", expression = "java(userEntity)")
    @Mapping(target = "comment", expression = "java(commentEntity)")
    @Mapping(target = "reactionType", expression = "java(reactionTypeEntity)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    CommentLikeEntity requestDTOToEntity(CommentLikeRequestDTO dto,
                                         @Context CommentEntity commentEntity,
                                         @Context ReactionTypeEntity reactionTypeEntity,
                                         @Context UserEntity userEntity);

    @Override
    default CommentLikeEntity requestDTOToEntity(CommentLikeRequestDTO dto) {
        return null;
    }

    @Override
    default CommentLikeEntity requestDtoToExistEntity(@MappingTarget CommentLikeEntity entity, CommentLikeRequestDTO requestDTO) {
        return null;
    }

    @Mapping(target = "reactionType", source = "reactionType.name")
    @Mapping(target = "commentId", source = "comment.uuid")
    CommentLikeResponseDTO entityToDTO(CommentLikeEntity entity);

    @Mapping(target = "reactionType", ignore = true)
    CommentLikeEntity dtoToEntity(CommentLikeResponseDTO dto);
}
