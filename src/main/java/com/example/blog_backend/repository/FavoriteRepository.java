package com.example.blog_backend.repository;

import com.example.blog_backend.core.repository.BaseRepository;
import com.example.blog_backend.entity.FavoriteEntity;
import com.example.blog_backend.entity.PostEntity;
import com.example.blog_backend.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends BaseRepository<FavoriteEntity> {
    int deleteByPostAndUser(PostEntity post, UserEntity user);
}
