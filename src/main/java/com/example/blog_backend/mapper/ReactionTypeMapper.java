package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.ReactionTypeEntity;
import com.example.blog_backend.model.requestDTO.ReactionTypeRequestDTO;
import com.example.blog_backend.model.responseDTO.ReactionTypeDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReactionTypeMapper extends IBaseMapper<ReactionTypeDTO, ReactionTypeEntity, ReactionTypeRequestDTO> {
}
