package com.example.blog_backend.service;

import com.example.blog_backend.core.service.BaseCrudService;
import com.example.blog_backend.entity.RoleEntity;
import com.example.blog_backend.model.requestDTO.RoleRequestDTO;
import com.example.blog_backend.model.responseDTO.RoleDTO;

public interface RoleService extends BaseCrudService<RoleEntity, RoleDTO, RoleRequestDTO> {
}
