package com.example.blog_backend.service.impl;


import com.example.blog_backend.core.service.impl.AbstractBaseCrudServiceImpl;
import com.example.blog_backend.entity.RoleEntity;
import com.example.blog_backend.mapper.RoleMapper;
import com.example.blog_backend.model.requestDTO.RoleRequestDTO;
import com.example.blog_backend.model.responseDTO.RoleResponseDTO;
import com.example.blog_backend.repository.RoleRepository;
import com.example.blog_backend.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends AbstractBaseCrudServiceImpl<
        RoleEntity,
        RoleResponseDTO,
        RoleRequestDTO,
        RoleMapper,
        RoleRepository>
        implements RoleService {

    public RoleServiceImpl(RoleMapper getMapper, RoleRepository getRepository) {
        super(getMapper, getRepository);
    }
}
