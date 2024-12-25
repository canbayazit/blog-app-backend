package com.example.blog_backend.controller;

import com.example.blog_backend.core.controller.impl.AbstractBaseCrudControllerImpl;
import com.example.blog_backend.model.requestDTO.CommentRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentResponseDTO;
import com.example.blog_backend.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/post/comment")
public class CommentController extends AbstractBaseCrudControllerImpl<
        CommentResponseDTO,
        CommentRequestDTO,
        CommentService> {
    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        super(commentService);
        this.commentService = commentService;
    }
    @Override
    @PreAuthorize("@permissionEvaluator.isCommentOwner(#uuid, authentication) && @permissionEvaluator.isCommentBelongsToPost(#uuid, #requestDTO.postId)")
    public ResponseEntity<CommentResponseDTO> update(@PathVariable UUID uuid, @Valid @RequestBody CommentRequestDTO requestDTO) {
        return super.update(uuid, requestDTO);
    }

    @Override
    @PreAuthorize("@permissionEvaluator.isCommentOwner(#uuid, authentication)")
    public ResponseEntity<Boolean> deleteByUUID(@PathVariable UUID uuid) {
        return super.deleteByUUID(uuid);
    }

    @GetMapping("/get-all-by-post/{postId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByPost(@PathVariable UUID postId) {
        return new ResponseEntity<>(commentService.getAllCommentsByPostUuid(postId), HttpStatus.OK);
    }
}
