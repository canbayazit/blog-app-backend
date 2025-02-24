package com.example.blog_backend.design.listener;

import com.example.blog_backend.design.event.CommentCreatedEvent;
import com.example.blog_backend.design.event.CommentRemovedEvent;
import com.example.blog_backend.service.PostStatisticService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class CommentEventListener {

    private final PostStatisticService postStatisticService;

    public CommentEventListener(PostStatisticService postStatisticService) {
        this.postStatisticService = postStatisticService;
    }

    /**
     * Yorum eklenmesi event’ini asenkron ve atomik şekilde işler.
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleCommentCreatedEvent(CommentCreatedEvent event) {
        postStatisticService.incrementPostCommentCount(event.getPostId(), 1);
        System.out.println("Post " + event.getPostId() + " için comment count artırıldı (asenkron).");
    }

    /**
     * Yorum silinmesi event’ini asenkron ve atomik şekilde işler.
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleCommentRemovedEvent(CommentRemovedEvent event) {
        // Silinen yorum sayısı kadar azaltılır.
        postStatisticService.decrementPostCommentCount(event.getPostId(), event.getRemovedCount());
        System.out.println("Post " + event.getPostId() + " için comment count " + event.getRemovedCount() + " azaltıldı (asenkron).");

    }
}