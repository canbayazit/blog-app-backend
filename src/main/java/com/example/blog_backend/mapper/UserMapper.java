package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.model.requestDTO.RegisterRequestDTO;
import com.example.blog_backend.model.requestDTO.UserProfileRequestDTO;
import com.example.blog_backend.model.responseDTO.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends IBaseMapper<UserResponseDTO, UserEntity, UserProfileRequestDTO> {
    UserEntity requestDTOToEntity(RegisterRequestDTO dto);
}
