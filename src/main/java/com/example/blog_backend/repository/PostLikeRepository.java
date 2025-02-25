package com.example.blog_backend.repository;

import com.example.blog_backend.core.repository.BaseRepository;
import com.example.blog_backend.entity.PostEntity;
import com.example.blog_backend.entity.PostLikeEntity;
import com.example.blog_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface PostLikeRepository extends BaseRepository<PostLikeEntity> {

    @Query("SELECT pl.post.uuid, pl.reactionType.name, COUNT(pl) " +
            "FROM PostLikeEntity pl " +
            "WHERE pl.post.uuid IN :postUuids " +
            "GROUP BY pl.post.uuid, pl.reactionType.name")
    List<Object[]> findReactionCountsByPostUuids(@Param("postUuids") List<UUID> postUuids);

    @Query("SELECT pl.post.uuid, pl.reactionType FROM PostLikeEntity pl " +
            "WHERE pl.user = :user " +
            "AND pl.post.uuid IN :postUuids")
    List<Object[]> findReactionTypesByUserAndPostUuids(@Param("user") UserEntity user,
                                                       @Param("postUuids") List<UUID> postUuids);

    int deleteByPostAndUser(PostEntity post, UserEntity user);
}
