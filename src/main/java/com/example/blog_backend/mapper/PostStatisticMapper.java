package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.PostStatisticEntity;
import com.example.blog_backend.model.requestDTO.PostStatisticRequestDTO;
import com.example.blog_backend.model.responseDTO.PostStatisticResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostStatisticMapper extends IBaseMapper<PostStatisticResponseDTO, PostStatisticEntity, PostStatisticRequestDTO> {
}
