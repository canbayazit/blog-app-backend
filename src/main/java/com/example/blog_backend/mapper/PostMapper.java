package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.PostEntity;
import com.example.blog_backend.model.requestDTO.PostRequestDTO;
import com.example.blog_backend.model.responseDTO.PostResponseDTO;
import com.example.blog_backend.repository.CategoryRepository;
import com.example.blog_backend.service.UserContextService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {UserContextService.class, CategoryRepository.class})
public interface PostMapper extends IBaseMapper<PostResponseDTO, PostEntity, PostRequestDTO> {
    @Mapping(target = "user", expression = "java(userContextService.getCurrentAuthenticatedUser())")
    @Mapping(source = "categoryIds", target = "categories")
    PostEntity requestDTOToEntity(PostRequestDTO dto);
    @Mapping(source = "categoryIds", target = "categories")
    PostEntity requestDtoToExistEntity(@MappingTarget PostEntity entity, PostRequestDTO requestDTO);
}
