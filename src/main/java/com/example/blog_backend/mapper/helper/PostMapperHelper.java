package com.example.blog_backend.mapper.helper;

import com.example.blog_backend.entity.PostEntity;
import com.example.blog_backend.mapper.qualifier.PostIdToPost;
import com.example.blog_backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostMapperHelper {

    private final PostRepository postRepository;

    @PostIdToPost
    public PostEntity mapPostIdToPost(UUID postId) {
        return postRepository.findByUuid(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found."));
    }
}
