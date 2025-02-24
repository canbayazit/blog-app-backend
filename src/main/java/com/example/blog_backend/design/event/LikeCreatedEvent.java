package com.example.blog_backend.design.event;

import com.example.blog_backend.model.enums.ContextType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LikeCreatedEvent extends ApplicationEvent {

    private final Long contextId;  // Post veya Comment ID'si
    private final ContextType contextType;
    private final int increment; // Genellikle 1

    public LikeCreatedEvent(Object source, Long contextId, ContextType contextType, int increment) {
        super(source);
        this.contextId = contextId;
        this.contextType = contextType;
        this.increment = increment;
    }
}
