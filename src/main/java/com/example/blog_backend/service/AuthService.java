package com.example.blog_backend.service;

import com.example.blog_backend.entity.RoleEntity;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.mapper.UserMapper;
import com.example.blog_backend.model.enums.RoleType;
import com.example.blog_backend.model.requestDTO.RegisterRequestDTO;
import com.example.blog_backend.repository.RoleRepository;
import com.example.blog_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService  {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public void saveUserByRole(RegisterRequestDTO registerRequestDTO, RoleType defaultRole) {

        UserEntity user = userMapper.requestDTOToEntity(registerRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleRepository.findByName(defaultRole.name()).get());
        user.setRoles(roles);
        userRepository.save(user);
    }
}
