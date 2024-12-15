package com.example.blog_backend.controller;

import com.example.blog_backend.core.controller.impl.BaseControllerImpl;
import com.example.blog_backend.model.requestDTO.PostRequestDTO;
import com.example.blog_backend.model.requestDTO.PostStatusRequestDTO;
import com.example.blog_backend.model.responseDTO.PostResponseDTO;
import com.example.blog_backend.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/post")
public class PostController extends BaseControllerImpl<
        PostResponseDTO,
        PostRequestDTO,
        PostService> {

    private final PostService postService;
    public PostController(PostService postService) {
        super(postService);
        this.postService = postService;
    }

    @Override
    @PreAuthorize("@permissionEvaluator.isPostOwner(#uuid, authentication)")
    public ResponseEntity<PostResponseDTO> update(@PathVariable UUID uuid, @Valid @RequestBody PostRequestDTO requestDTO) {
        return super.update(uuid, requestDTO);
    }

    @Override
    @PreAuthorize("@permissionEvaluator.isPostOwner(#uuid, authentication)")
    public ResponseEntity<Boolean> deleteByUUID(@PathVariable UUID uuid) {
        return super.deleteByUUID(uuid);
    }

    @PreAuthorize("@permissionEvaluator.isPostOwner(#uuid, authentication)")
    @PutMapping("/publish-request/{uuid}")
    public ResponseEntity<PostResponseDTO> sendPublishRequest(@PathVariable UUID uuid) {
        PostResponseDTO publishedPost = postService.sendPublishRequest(uuid);
        if (publishedPost != null) {
            return new ResponseEntity<>(publishedPost, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/my-posts")
    public ResponseEntity<List<PostResponseDTO>> getMyPosts(@Valid @RequestBody PostStatusRequestDTO status) {
        List<PostResponseDTO> drafts = postService.getMyPostsByStatus(status);
        return new ResponseEntity<>(drafts, HttpStatus.OK);
    }
}
