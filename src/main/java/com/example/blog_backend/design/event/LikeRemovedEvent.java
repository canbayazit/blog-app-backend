package com.example.blog_backend.design.event;

import com.example.blog_backend.model.enums.ContextType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LikeRemovedEvent extends ApplicationEvent {

    private final Long contextId;
    private final ContextType contextType;
    private final int decrement; // Genellikle 1

    public LikeRemovedEvent(Object source, Long contextId, ContextType contextType, int decrement) {
        super(source);
        this.contextId = contextId;
        this.contextType = contextType;
        this.decrement = decrement;
    }
}
