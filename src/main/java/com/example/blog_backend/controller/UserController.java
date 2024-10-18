package com.example.blog_backend.controller;

import com.example.blog_backend.core.controller.BaseController;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.mapper.UserMapper;
import com.example.blog_backend.model.requestDTO.UserProfileRequestDTO;
import com.example.blog_backend.model.responseDTO.UserResponseDTO;
import com.example.blog_backend.repository.UserRepository;
import com.example.blog_backend.service.UserService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")
public class UserController extends BaseController<
        UserEntity,
        UserResponseDTO,
        UserProfileRequestDTO,
        UserMapper,
        UserRepository,
        UserService> {
    private final UserService userService;
    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }


}
