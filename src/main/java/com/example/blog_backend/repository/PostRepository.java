package com.example.blog_backend.repository;

import com.example.blog_backend.core.repository.BaseRepository;
import com.example.blog_backend.entity.PostEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends BaseRepository<PostEntity> {
    boolean existsByUuidAndUserEmail(UUID uuid, String email);
}
