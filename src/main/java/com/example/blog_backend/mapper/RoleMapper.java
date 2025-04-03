package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.RoleEntity;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.model.requestDTO.RoleRequestDTO;
import com.example.blog_backend.model.responseDTO.RoleDTO;
import com.example.blog_backend.model.responseDTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.SETTER)
public abstract class RoleMapper implements IBaseMapper<RoleDTO, RoleEntity, RoleRequestDTO> {
    protected  UserMapper userMapper;

    public RoleMapper(@Lazy UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    @Lazy
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Named("mapUserEntitySetToDtoSet")
    public Set<UserDTO> mapUserEntitySetToDtoSet(Set<UserEntity> users) {
        if (users == null) {
            return null;
        }
        return users.stream()
                .map(userMapper::toDtoWithoutChildren)
                .collect(Collectors.toSet());
    }

    @Named("mapUserDtoSetToEntitySet")
    public Set<UserEntity> mapUserDtoSetToEntitySet(Set<UserDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(userMapper::dtoToEntity)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "users", ignore = true)
    public abstract RoleEntity requestDTOToEntity(RoleRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "users", ignore = true)
    public abstract RoleEntity requestDtoToExistEntity(@MappingTarget RoleEntity entity, RoleRequestDTO requestDTO);

    @Mapping(source = "users", target = "users", qualifiedByName = "mapUserEntitySetToDtoSet")
    public abstract RoleDTO entityToDTO(RoleEntity entity);

    @Mapping(source = "users", target = "users", qualifiedByName = "mapUserDtoSetToEntitySet")
    public abstract RoleEntity dtoToEntity(RoleDTO dto);

    @Named("toDtoWithoutUser")
    @Mapping(target = "users", ignore = true)
    public abstract RoleDTO toDtoWithoutUser(RoleEntity entity);

}
