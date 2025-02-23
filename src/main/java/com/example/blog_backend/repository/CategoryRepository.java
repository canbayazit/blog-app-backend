package com.example.blog_backend.repository;

import com.example.blog_backend.core.repository.BaseRepository;
import com.example.blog_backend.entity.CategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface CategoryRepository extends BaseRepository<CategoryEntity> {
    long countByUuidIn(Collection<UUID> uuids);
}
