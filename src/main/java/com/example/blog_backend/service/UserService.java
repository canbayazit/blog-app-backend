package com.example.blog_backend.service;

import com.example.blog_backend.core.service.BaseService;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.mapper.UserMapper;
import com.example.blog_backend.model.requestDTO.UserProfileRequestDTO;
import com.example.blog_backend.model.responseDTO.UserResponseDTO;
import com.example.blog_backend.repository.RoleRepository;
import com.example.blog_backend.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService extends BaseService<
        UserEntity,
        UserResponseDTO,
        UserProfileRequestDTO,
        UserMapper,
        UserRepository> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserService(UserMapper getMapper, UserRepository userRepository,
                       RoleRepository roleRepository, UserMapper userMapper) {
        super(getMapper, userRepository);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

}
