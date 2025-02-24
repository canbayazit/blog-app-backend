package com.example.blog_backend.design.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentCreatedEvent extends ApplicationEvent {

    private final Long postId;
    private final Long commentId;

    public CommentCreatedEvent(Object source, Long postId, Long commentId) {
        super(source);
        this.postId = postId;
        this.commentId = commentId;
    }
}
