package com.example.blog_backend.repository;

import com.example.blog_backend.core.repository.BaseRepository;
import com.example.blog_backend.entity.CommentEntity;
import com.example.blog_backend.entity.CommentLikeEntity;
import com.example.blog_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentLikeRepository extends BaseRepository<CommentLikeEntity> {
    @Query("SELECT cl.comment.uuid, cl.reactionType.name, COUNT(cl) " +
            "FROM CommentLikeEntity cl " +
            "WHERE cl.comment.uuid IN :commentUuids " +
            "GROUP BY cl.comment.uuid, cl.reactionType.name")
    List<Object[]> findReactionCountsByCommentUuids(@Param("commentUuids") List<UUID> commentUuids);

    @Query("SELECT cl.comment.uuid, cl.reactionType FROM CommentLikeEntity cl " +
            "WHERE cl.user = :user " +
            "AND cl.comment.uuid IN :commentUuids")
    List<Object[]> findReactionTypesByUserAndCommentUuids(@Param("user") UserEntity user,
                                                       @Param("commentUuids") List<UUID> commentUuids);
    int deleteByCommentAndUser(CommentEntity post, UserEntity user);
}
