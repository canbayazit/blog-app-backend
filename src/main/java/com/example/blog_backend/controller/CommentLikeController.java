package com.example.blog_backend.controller;

import com.example.blog_backend.core.controller.impl.AbstractBaseCrudControllerImpl;
import com.example.blog_backend.entity.CommentLikeEntity;
import com.example.blog_backend.model.requestDTO.CommentLikeRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentLikeResponseDTO;
import com.example.blog_backend.service.CommentLikeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/comment/reaction")
public class CommentLikeController extends AbstractBaseCrudControllerImpl<
        CommentLikeEntity,
        CommentLikeResponseDTO,
        CommentLikeRequestDTO,
        CommentLikeService> {
    public CommentLikeController(CommentLikeService commentLikeService) {
        super(commentLikeService);
    }
}
