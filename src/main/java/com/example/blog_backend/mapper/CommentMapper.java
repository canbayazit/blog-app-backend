package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.CommentEntity;
import com.example.blog_backend.mapper.helper.PostMapperHelper;
import com.example.blog_backend.mapper.qualifier.PostIdToPost;
import com.example.blog_backend.model.requestDTO.CommentRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentResponseDTO;
import com.example.blog_backend.service.UserContextService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {UserContextService.class, PostMapperHelper.class})
public interface CommentMapper extends IBaseMapper<CommentResponseDTO, CommentEntity, CommentRequestDTO> {

    @Mapping(source = "postId", target = "post", qualifiedBy = PostIdToPost.class)
    @Mapping(target = "user", expression = "java(userContextService.getCurrentAuthenticatedUser())")
    CommentEntity requestDTOToEntity(CommentRequestDTO dto);
}
