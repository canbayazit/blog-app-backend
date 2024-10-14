package com.example.blog_backend.controller;

import com.example.blog_backend.core.controller.BaseController;
import com.example.blog_backend.entity.RoleEntity;
import com.example.blog_backend.mapper.RoleMapper;
import com.example.blog_backend.model.requestDTO.RoleRequestDTO;
import com.example.blog_backend.model.responseDTO.RoleResponseDTO;
import com.example.blog_backend.repository.RoleRepository;
import com.example.blog_backend.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("role")
public class RoleController extends BaseController<
        RoleEntity,
        RoleResponseDTO,
        RoleRequestDTO,
        RoleMapper,
        RoleRepository,
        RoleService
        > {

    public RoleController(RoleService getService) {
        super(getService);
    }
}
