package com.example.blog_backend.util.security;

import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface PermissionEvaluator {
    boolean isPostOwner(UUID uuid, Authentication authentication);
    boolean isCommentOwner(UUID uuid, Authentication authentication);
    boolean isCommentBelongsToPost(UUID commentId, UUID postId);
    boolean isSelf(UUID userId);
}
