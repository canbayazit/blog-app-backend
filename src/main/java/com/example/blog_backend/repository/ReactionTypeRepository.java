package com.example.blog_backend.repository;

import com.example.blog_backend.core.repository.BaseRepository;
import com.example.blog_backend.entity.ReactionTypeEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactionTypeRepository extends BaseRepository<ReactionTypeEntity> {
    Optional<ReactionTypeEntity> findByName(String name);
}
