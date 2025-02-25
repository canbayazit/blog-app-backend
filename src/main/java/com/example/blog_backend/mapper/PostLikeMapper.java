package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.*;
import com.example.blog_backend.model.requestDTO.PostLikeRequestDTO;
import com.example.blog_backend.model.responseDTO.PostLikeResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {PostMapper.class})
public interface PostLikeMapper extends IBaseMapper<PostLikeResponseDTO, PostLikeEntity, PostLikeRequestDTO> {

    @Mapping(target = "user", expression = "java(userEntity)")
    @Mapping(target = "post", expression = "java(postEntity)")
    @Mapping(target = "reactionType", expression = "java(reactionTypeEntity)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    PostLikeEntity requestDTOToEntity(PostLikeRequestDTO requestDTO,
                                      @Context PostEntity postEntity,
                                      @Context ReactionTypeEntity reactionTypeEntity,
                                      @Context UserEntity userEntity);

    @Override
    default PostLikeEntity requestDTOToEntity(PostLikeRequestDTO dto) {
        return null;
    }

    @Override
    default PostLikeEntity requestDtoToExistEntity(@MappingTarget PostLikeEntity entity, PostLikeRequestDTO requestDTO) {
        return null;
    }

    @Mapping(target = "reactionType", source = "reactionType.name")
    @Mapping(target = "postId", source = "post.uuid")
    PostLikeResponseDTO entityToDTO(PostLikeEntity entity);

    @Mapping(target = "reactionType", ignore = true)
    PostLikeEntity dtoToEntity(PostLikeResponseDTO dto);

}
