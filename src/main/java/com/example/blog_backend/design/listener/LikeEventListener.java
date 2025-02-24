package com.example.blog_backend.design.listener;

import com.example.blog_backend.design.event.LikeCreatedEvent;
import com.example.blog_backend.design.event.LikeRemovedEvent;
import com.example.blog_backend.design.strategy.LikeProcessingStrategy;
import com.example.blog_backend.model.enums.ContextType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class LikeEventListener {

    private final Map<ContextType, LikeProcessingStrategy> strategyMap;

    public LikeEventListener(List<LikeProcessingStrategy> strategies) {
        // Tüm stratejileri context tipine göre haritalandırıyoruz.
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(LikeProcessingStrategy::getSupportedContext, Function.identity()));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleLikeCreatedEvent(LikeCreatedEvent event) {
        LikeProcessingStrategy strategy = strategyMap.get(event.getContextType());
        if (strategy != null) {
            strategy.processLikeCreated(event);
        } else {
            throw new IllegalArgumentException("Unsupported context: " + event.getContextType());
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleLikeRemovedEvent(LikeRemovedEvent event) {
        LikeProcessingStrategy strategy = strategyMap.get(event.getContextType());
        if (strategy != null) {
            strategy.processLikeRemoved(event);
        } else {
            throw new IllegalArgumentException("Unsupported context: " + event.getContextType());
        }
    }
}
