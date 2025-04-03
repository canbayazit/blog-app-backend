package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.*;
import com.example.blog_backend.model.requestDTO.PostLikeRequestDTO;
import com.example.blog_backend.model.responseDTO.PostLikeDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {PostMapper.class, UserMapper.class, ReactionTypeMapper.class})
public interface PostLikeMapper extends IBaseMapper<PostLikeDTO, PostLikeEntity, PostLikeRequestDTO> {

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

    @Mapping(target = "postId", source = "post.uuid")
    PostLikeDTO entityToDTO(PostLikeEntity entity);
}
