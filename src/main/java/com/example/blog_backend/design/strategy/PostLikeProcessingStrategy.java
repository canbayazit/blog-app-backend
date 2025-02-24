package com.example.blog_backend.design.strategy;

import com.example.blog_backend.design.event.LikeCreatedEvent;
import com.example.blog_backend.design.event.LikeRemovedEvent;
import com.example.blog_backend.model.enums.ContextType;
import com.example.blog_backend.service.PostStatisticService;
import org.springframework.stereotype.Component;

@Component
public class PostLikeProcessingStrategy implements LikeProcessingStrategy{
    private final PostStatisticService postStatisticService;

    public PostLikeProcessingStrategy(PostStatisticService postStatisticService) {
        this.postStatisticService = postStatisticService;
    }

    @Override
    public ContextType getSupportedContext() {
        return ContextType.POST;
    }

    @Override
    public void processLikeCreated(LikeCreatedEvent event) {
        postStatisticService.incrementPostLikeCount(event.getContextId(), event.getIncrement());
        System.out.println("Post " + event.getContextId() + " için like count artırıldı (Strategy).");
    }

    @Override
    public void processLikeRemoved(LikeRemovedEvent event) {
        postStatisticService.decrementPostLikeCount(event.getContextId(), event.getDecrement());
        System.out.println("Post " + event.getContextId() + " için like count azaltıldı (Strategy).");
    }
}
