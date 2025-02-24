package com.example.blog_backend.design.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentRemovedEvent extends ApplicationEvent {

    private final Long postId;
    private final int removedCount; // Silinen yorum sayısı (örneğin, parent ve varsa child’lar)

    public CommentRemovedEvent(Object source, Long postId, int removedCount) {
        super(source);
        this.postId = postId;
        this.removedCount = removedCount;
    }
}
