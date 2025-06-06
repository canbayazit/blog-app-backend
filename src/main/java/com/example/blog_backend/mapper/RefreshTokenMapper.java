package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.RefreshTokenEntity;
import com.example.blog_backend.model.requestDTO.RefreshTokenRequestDTO;
import com.example.blog_backend.model.responseDTO.RefreshTokenResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RefreshTokenMapper extends IBaseMapper<RefreshTokenResponseDTO, RefreshTokenEntity, RefreshTokenRequestDTO> {
}
