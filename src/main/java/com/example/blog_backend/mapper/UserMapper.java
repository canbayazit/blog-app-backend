package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.model.requestDTO.*;
import com.example.blog_backend.model.responseDTO.UserDTO;
import com.example.blog_backend.model.responseDTO.UserSummaryDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {RoleMapper.class})
public interface UserMapper extends IBaseMapper<UserDTO, UserEntity, UserRequestDTO> {

    UserEntity requestDTOToEntity(RegisterRequestDTO dto);
    UserEntity requestDtoToExistEntity(@MappingTarget UserEntity entity, UserEmailUpdateRequestDTO dto);
    UserEntity requestDtoToExistEntity(@MappingTarget UserEntity entity, String dto);
    UserSummaryDTO entityToUserSummaryDTO(UserEntity entity);

    @Named("toDtoWithoutChildren")
    @Mapping(target = "roles", ignore = true)
    UserDTO toDtoWithoutChildren(UserEntity entity);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "toDtoWithoutUser")
    UserDTO entityToDTO(UserEntity entity);
}
