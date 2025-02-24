package com.example.blog_backend.design.strategy;

import com.example.blog_backend.design.event.LikeCreatedEvent;
import com.example.blog_backend.design.event.LikeRemovedEvent;
import com.example.blog_backend.model.enums.ContextType;

public interface LikeProcessingStrategy {
    /**
     * Bu stratejinin desteklediği context tipini döner.
     */
    ContextType getSupportedContext();

    /**
     * Like eklenmesi olayını işleyerek ilgili istatistiği günceller.
     */
    void processLikeCreated(LikeCreatedEvent event);

    /**
     * Like silinmesi olayını işleyerek ilgili istatistiği günceller.
     */
    void processLikeRemoved(LikeRemovedEvent event);
}
