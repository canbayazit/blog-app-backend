package com.example.blog_backend.service;


import com.example.blog_backend.core.service.BaseService;
import com.example.blog_backend.entity.RoleEntity;
import com.example.blog_backend.mapper.RoleMapper;
import com.example.blog_backend.model.requestDTO.RoleRequestDTO;
import com.example.blog_backend.model.responseDTO.RoleResponseDTO;
import com.example.blog_backend.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<
        RoleEntity,
        RoleResponseDTO,
        RoleRequestDTO,
        RoleMapper,
        RoleRepository> {

    public RoleService(RoleMapper getMapper, RoleRepository getRepository) {
        super(getMapper, getRepository);
    }
}
