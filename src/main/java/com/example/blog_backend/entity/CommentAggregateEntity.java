package com.example.blog_backend.entity;

import com.example.blog_backend.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment_statistic")
public class CommentAggregateEntity extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "comment_id", nullable = false, unique = true)
    private CommentEntity comment;

    @Column(nullable = false, name = "like_count")
    private int likeCount = 0;

    @Column(name = "child_count")
    private int childCount;

}
