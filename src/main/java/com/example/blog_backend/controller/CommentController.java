package com.example.blog_backend.controller;

import com.example.blog_backend.core.controller.impl.BaseControllerImpl;
import com.example.blog_backend.model.requestDTO.CommentRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentResponseDTO;
import com.example.blog_backend.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/comment")
public class CommentController extends BaseControllerImpl<
        CommentResponseDTO,
        CommentRequestDTO,
        CommentService> {
    public CommentController(CommentService getService) {
        super(getService);
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
}
