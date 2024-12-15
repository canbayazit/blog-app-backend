package com.example.blog_backend.controller;

import com.example.blog_backend.core.controller.impl.BaseControllerImpl;
import com.example.blog_backend.model.requestDTO.PostRequestDTO;
import com.example.blog_backend.model.responseDTO.PostResponseDTO;
import com.example.blog_backend.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/post")
public class PostController extends BaseControllerImpl<
        PostResponseDTO,
        PostRequestDTO,
        PostService> {

    public PostController(PostService postService) {
        super(postService);
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
}
