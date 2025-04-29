package com.example.blog_backend.controller;

import com.example.blog_backend.core.controller.impl.AbstractBaseCrudControllerImpl;
import com.example.blog_backend.entity.CommentEntity;
import com.example.blog_backend.model.requestDTO.CommentChildRequestDTO;
import com.example.blog_backend.model.requestDTO.CommentRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentDTO;
import com.example.blog_backend.service.CommentService;
import com.example.blog_backend.util.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/post/comment")
public class CommentController extends AbstractBaseCrudControllerImpl<
        CommentEntity,
        CommentDTO,
        CommentRequestDTO,
        CommentService> {
    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        super(commentService);
        this.commentService = commentService;
    }

    @PostMapping("/reply/{targetCommentUuid}")
    public ResponseEntity<ApiResponse<CommentDTO>> createReply(
            @PathVariable UUID targetCommentUuid,
            @Valid @RequestBody CommentChildRequestDTO requestDTO
    ) {
        CommentDTO dto = commentService.createReply(targetCommentUuid, requestDTO);
        ApiResponse<CommentDTO> response = ApiResponse.success(dto, "Data created successfully.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("@permissionEvaluator.isCommentOwner(#uuid, authentication) && @permissionEvaluator.isCommentBelongsToPost(#uuid, #requestDTO.postId)")
    public ResponseEntity<ApiResponse<CommentDTO>> update(@PathVariable UUID uuid, @Valid @RequestBody CommentRequestDTO requestDTO) {
        return super.update(uuid, requestDTO);
    }

    @Override
    @PreAuthorize("@permissionEvaluator.isCommentOwner(#uuid, authentication)")
    public ResponseEntity<ApiResponse<Boolean>> deleteByUUID(@PathVariable UUID uuid) {
        return super.deleteByUUID(uuid);
    }
}
