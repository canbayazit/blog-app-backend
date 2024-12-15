package com.example.blog_backend.repository;

import com.example.blog_backend.core.repository.BaseRepository;
import com.example.blog_backend.entity.PostEntity;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.model.enums.PostStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends BaseRepository<PostEntity> {
    List<PostEntity> findByUserAndStatus(UserEntity author, PostStatus status);
    boolean existsByUuidAndUserEmail(UUID uuid, String email);
}
