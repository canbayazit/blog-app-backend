package com.example.blog_backend.repository;

import com.example.blog_backend.core.repository.BaseRepository;
import com.example.blog_backend.entity.RefreshTokenEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends BaseRepository<RefreshTokenEntity> {
}
