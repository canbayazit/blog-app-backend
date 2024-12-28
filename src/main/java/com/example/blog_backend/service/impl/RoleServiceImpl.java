package com.example.blog_backend.service.impl;


import com.example.blog_backend.core.service.impl.AbstractBaseCrudServiceImpl;
import com.example.blog_backend.entity.RoleEntity;
import com.example.blog_backend.mapper.RoleMapper;
import com.example.blog_backend.model.requestDTO.RoleRequestDTO;
import com.example.blog_backend.model.responseDTO.RoleResponseDTO;
import com.example.blog_backend.repository.RoleRepository;
import com.example.blog_backend.service.RoleService;
import com.example.blog_backend.specification.RoleSpecification;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends AbstractBaseCrudServiceImpl<
        RoleEntity,
        RoleResponseDTO,
        RoleRequestDTO,
        RoleMapper,
        RoleRepository,
        RoleSpecification>
        implements RoleService {

    public RoleServiceImpl(RoleMapper getMapper, RoleRepository getRepository, RoleSpecification roleSpecification) {
        super(getMapper, getRepository, roleSpecification);
    }
}
