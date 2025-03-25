package com.example.blog_backend.controller;

import com.example.blog_backend.core.controller.impl.AbstractBaseCrudControllerImpl;
import com.example.blog_backend.entity.FavoriteEntity;
import com.example.blog_backend.model.requestDTO.FavoriteRequestDTO;
import com.example.blog_backend.model.responseDTO.FavoriteDTO;
import com.example.blog_backend.service.FavoriteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/favorite")
public class FavoriteController extends AbstractBaseCrudControllerImpl<
        FavoriteEntity,
        FavoriteDTO,
        FavoriteRequestDTO,
        FavoriteService> {
    public FavoriteController(FavoriteService favoriteService) {
        super(favoriteService);
    }
}
