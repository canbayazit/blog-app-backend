package com.example.blog_backend.controller;

import com.example.blog_backend.core.controller.impl.AbstractBaseCrudControllerImpl;
import com.example.blog_backend.entity.PostEntity;
import com.example.blog_backend.model.requestDTO.PostRequestDTO;
import com.example.blog_backend.model.requestDTO.PostStatusRequestDTO;
import com.example.blog_backend.model.responseDTO.ApiResponseDTO;
import com.example.blog_backend.model.responseDTO.PostDTO;
import com.example.blog_backend.service.PostService;
import com.example.blog_backend.util.response.ApiResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/post")
public class PostController extends AbstractBaseCrudControllerImpl<
        PostEntity,
        PostDTO,
        PostRequestDTO,
        PostService> {

    private final PostService postService;
    public PostController(PostService postService) {
        super(postService);
        this.postService = postService;
    }

    @Override
    @PreAuthorize("@permissionEvaluator.isPostOwner(#uuid, authentication)")
    public ResponseEntity<ApiResponseDTO<PostDTO>> update(@PathVariable UUID uuid, @Valid @RequestBody PostRequestDTO requestDTO) {
        return super.update(uuid, requestDTO);
    }

    @Override
    @PreAuthorize("@permissionEvaluator.isPostOwner(#uuid, authentication)")
    public ResponseEntity<ApiResponseDTO<Boolean>> deleteByUUID(@PathVariable UUID uuid) {
        return super.deleteByUUID(uuid);
    }

    @PreAuthorize("@permissionEvaluator.isPostOwner(#uuid, authentication)")
    @PutMapping("/publish-request/{uuid}")
    public ResponseEntity<ApiResponseDTO<PostDTO>> sendPublishRequest(@PathVariable UUID uuid) {
        PostDTO publishedPost = postService.sendPublishRequest(uuid);
        if (publishedPost != null) {
            ApiResponseDTO<PostDTO> response = ApiResponseUtil.success(publishedPost, "Publish request sent successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponseDTO<PostDTO> response = ApiResponseUtil.error("Data not found or publish request failed.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/my-posts")
    public ResponseEntity<ApiResponseDTO<List<PostDTO>>> getMyPosts(@Valid @RequestBody PostStatusRequestDTO status) {
        List<PostDTO> drafts = postService.getMyPostsByStatus(status);
        ApiResponseDTO<List<PostDTO>> response = ApiResponseUtil.success(drafts, "Posts retrieved successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
