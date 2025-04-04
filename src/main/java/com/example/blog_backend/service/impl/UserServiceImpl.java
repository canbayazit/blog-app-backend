package com.example.blog_backend.service.impl;

import com.example.blog_backend.core.service.impl.AbstractBaseCrudServiceImpl;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.mapper.UserMapper;
import com.example.blog_backend.model.requestDTO.*;
import com.example.blog_backend.model.responseDTO.UserDTO;
import com.example.blog_backend.repository.UserRepository;
import com.example.blog_backend.service.UserContextService;
import com.example.blog_backend.service.UserService;
import com.example.blog_backend.specification.UserSpecification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends AbstractBaseCrudServiceImpl<
        UserEntity,
        UserDTO,
        UserRequestDTO,
        UserMapper,
        UserRepository,
        UserSpecification>
        implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserContextService userContextService;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           UserContextService userContextService,
                           UserSpecification userSpecification) {
        super(userMapper, userRepository, userSpecification);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userContextService = userContextService;
    }

    @Override
    @Transactional
    public Boolean updateEmail(UserEmailUpdateRequestDTO userEmailRequestDTO) {
        UserEntity currentUser = userContextService.getRequiredAuthenticatedUser();
        if (currentUser != null) {
            currentUser = userMapper.requestDtoToExistEntity(currentUser, userEmailRequestDTO);
            userRepository.save(currentUser);
            return true;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void updatePassword(UserPasswordUpdateRequestDTO passwordUpdateRequestDTO) {
        UserEntity currentUser = userContextService.getRequiredAuthenticatedUser();
        if (!passwordEncoder.matches(passwordUpdateRequestDTO.getCurrentPassword(), currentUser.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (!passwordUpdateRequestDTO.getNewPassword().equals(passwordUpdateRequestDTO.getConfirmPassword())){
            throw new IllegalStateException("Passwords do not match");
        }

        currentUser.setPassword(passwordEncoder.encode(passwordUpdateRequestDTO.getNewPassword()));
        userRepository.save(currentUser);
    }
}
