package com.example.blog_backend.service;

import com.example.blog_backend.core.service.BaseCrudService;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.model.requestDTO.*;
import com.example.blog_backend.model.responseDTO.UserDTO;

public interface UserService extends BaseCrudService<UserEntity, UserDTO, UserRequestDTO> {
    Boolean updateEmail(UserEmailUpdateRequestDTO userEmailRequestDTO);
    void updatePassword(UserPasswordUpdateRequestDTO passwordUpdateRequestDTO);
}
