package com.example.blog_backend.util.security;

import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.repository.CommentRepository;
import com.example.blog_backend.repository.PostRepository;
import com.example.blog_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component(value = "permissionEvaluator")
@RequiredArgsConstructor
public class PermissionEvaluatorImpl implements PermissionEvaluator{

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    @Override
    public boolean isPostOwner(UUID uuid, Authentication authentication) {
        boolean isPostOwner = postRepository.existsByUuidAndUserEmail(uuid, authentication.getName());
        if (!isPostOwner){
            throw new AccessDeniedException("You are not the owner of the post.");
        } else {
            return true;
        }
    }

    @Override
    public boolean isCommentOwner(UUID uuid, Authentication authentication) {
        boolean isCommentOwner = commentRepository.existsByUuidAndUserEmail(uuid, authentication.getName());
        if (!isCommentOwner){
            throw new AccessDeniedException("You are not the owner of the comment.");
        } else {
            return true;
        }
    }

    @Override
    public boolean isCommentBelongsToPost(UUID commentId, UUID postId) {
        boolean isCommentBelongsToPost = commentRepository.existsByUuidAndPostUuid(commentId, postId);
        if (!isCommentBelongsToPost){
            throw new AccessDeniedException("The comment does not belong to the specified post. Please check the post ID and try again.");
        } else {
            return true;
        }
    }

    @Override
    public boolean isSelf(UUID userId) {
        UserEntity currentUser = userRepository.findByUuid(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return currentUser.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
