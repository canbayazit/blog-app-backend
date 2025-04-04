package com.example.blog_backend.controller;

import com.example.blog_backend.core.controller.impl.AbstractBaseCrudControllerImpl;
import com.example.blog_backend.entity.PostLikeEntity;
import com.example.blog_backend.model.requestDTO.PostLikeRequestDTO;
import com.example.blog_backend.model.responseDTO.PostLikeDTO;
import com.example.blog_backend.service.PostLikeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/post/reaction")
public class PostLikeController extends AbstractBaseCrudControllerImpl<
        PostLikeEntity,
        PostLikeDTO,
        PostLikeRequestDTO,
        PostLikeService> {
    public PostLikeController(PostLikeService likeService) {
        super(likeService);
    }

}
