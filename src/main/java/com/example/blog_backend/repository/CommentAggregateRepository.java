package com.example.blog_backend.repository;

import com.example.blog_backend.core.repository.BaseRepository;
import com.example.blog_backend.entity.CommentAggregateEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentAggregateRepository extends BaseRepository<CommentAggregateEntity> {
    @Modifying
    @Query("UPDATE CommentAggregateEntity c SET c.likeCount = c.likeCount + :inc WHERE c.comment.id = :commentId")
    void incrementCommentLikeCount(@Param("commentId") Long commentId, @Param("inc") int inc);

    @Modifying
    @Query("UPDATE CommentAggregateEntity c SET c.likeCount = c.likeCount - :dec WHERE c.comment.id = :commentId")
    void decrementCommentLikeCount(@Param("commentId") Long commentId, @Param("dec") int dec);


    @Modifying
    @Query("UPDATE CommentAggregateEntity c SET c.childCount = c.childCount + :inc WHERE c.comment.id = :commentId")
    void incrementChildCount(@Param("commentId") Long commentId, @Param("inc") int dec);

    @Modifying
    @Query("UPDATE CommentAggregateEntity c SET c.childCount = c.childCount - :dec WHERE c.comment.id = :commentId")
    void decrementChildCount(@Param("commentId") Long commentId, @Param("dec") int dec);
}
