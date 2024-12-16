package com.example.blog_backend.repository;

import com.example.blog_backend.core.repository.BaseRepository;
import com.example.blog_backend.entity.CommentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository  extends BaseRepository<CommentEntity> {
    boolean existsByUuidAndUserEmail(UUID uuid, String email);
    boolean existsByUuidAndPostUuid(UUID commentId, UUID postId);

    @Query("SELECT c FROM CommentEntity c WHERE c.post.uuid = :postId")
    List<CommentEntity> findAllByPostUuid(@Param("postId") UUID postId);
}
