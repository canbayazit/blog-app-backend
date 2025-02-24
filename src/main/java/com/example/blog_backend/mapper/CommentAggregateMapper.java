package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.CommentAggregateEntity;
import com.example.blog_backend.model.requestDTO.CommentAggregateRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentAggregateResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentAggregateMapper extends IBaseMapper<CommentAggregateResponseDTO, CommentAggregateEntity, CommentAggregateRequestDTO> {
}
