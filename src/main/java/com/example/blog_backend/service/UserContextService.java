package com.example.blog_backend.service;

import com.example.blog_backend.entity.UserEntity;

import java.util.Optional;

public interface UserContextService {
    UserEntity getRequiredAuthenticatedUser();
    Optional<UserEntity> getOptionalAuthenticatedUser();
}
