package com.example.blog_backend.design.strategy;

import com.example.blog_backend.design.event.LikeCreatedEvent;
import com.example.blog_backend.design.event.LikeRemovedEvent;
import com.example.blog_backend.model.enums.ContextType;
import com.example.blog_backend.service.CommentAggregateService;
import org.springframework.stereotype.Component;

@Component
public class CommentLikeProcessingStrategy implements LikeProcessingStrategy{
    private final CommentAggregateService commentAggregateService;

    public CommentLikeProcessingStrategy(CommentAggregateService commentAggregateService) {
        this.commentAggregateService = commentAggregateService;
    }

    @Override
    public ContextType getSupportedContext() {
        return ContextType.COMMENT;
    }

    @Override
    public void processLikeCreated(LikeCreatedEvent event) {
        commentAggregateService.incrementCommentLikeCount(event.getContextId(), event.getIncrement());
        System.out.println("Post " + event.getContextId() + " için like count artırıldı (Strategy).");
    }

    @Override
    public void processLikeRemoved(LikeRemovedEvent event) {
        commentAggregateService.decrementCommentLikeCount(event.getContextId(), event.getDecrement());
        System.out.println("Post " + event.getContextId() + " için like count azaltıldı (Strategy).");
    }
}
