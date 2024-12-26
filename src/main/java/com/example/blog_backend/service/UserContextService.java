package com.example.blog_backend.service;

import com.example.blog_backend.entity.UserEntity;

public interface UserContextService {
    UserEntity getCurrentAuthenticatedUser();
}
