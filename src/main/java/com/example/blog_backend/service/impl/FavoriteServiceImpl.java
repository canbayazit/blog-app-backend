package com.example.blog_backend.service.impl;

import com.example.blog_backend.core.service.impl.AbstractBaseCrudServiceImpl;
import com.example.blog_backend.entity.*;
import com.example.blog_backend.mapper.FavoriteMapper;
import com.example.blog_backend.model.requestDTO.FavoriteRequestDTO;
import com.example.blog_backend.model.responseDTO.FavoriteDTO;
import com.example.blog_backend.repository.FavoriteRepository;
import com.example.blog_backend.repository.PostRepository;
import com.example.blog_backend.service.FavoriteService;
import com.example.blog_backend.service.UserContextService;
import com.example.blog_backend.specification.FavoriteSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FavoriteServiceImpl extends AbstractBaseCrudServiceImpl<
        FavoriteEntity,
        FavoriteDTO,
        FavoriteRequestDTO,
        FavoriteMapper,
        FavoriteRepository,
        FavoriteSpecification> implements FavoriteService {

    private final FavoriteMapper favoriteMapper;
    private final FavoriteRepository favoriteRepository;
    private final PostRepository postRepository;
    private final UserContextService userContextService;
    public FavoriteServiceImpl(FavoriteMapper favoriteMapper,
                               FavoriteRepository favoriteRepository,
                               FavoriteSpecification favoriteSpecification,
                               PostRepository postRepository,
                               UserContextService userContextService) {
        super(favoriteMapper, favoriteRepository, favoriteSpecification);
        this.favoriteMapper = favoriteMapper;
        this.favoriteRepository = favoriteRepository;
        this.postRepository = postRepository;
        this.userContextService = userContextService;
    }

    @Override
    @Transactional
    public FavoriteDTO save(FavoriteRequestDTO requestDTO) {
        PostEntity postEntity = postRepository.findByUuid(requestDTO.getPost().getUuid())
                .orElseThrow(() -> new EntityNotFoundException("Post not found."));
        UserEntity currentUser = userContextService.getRequiredAuthenticatedUser();
        FavoriteEntity entity = favoriteMapper.requestDTOToEntity(requestDTO, postEntity, currentUser);
        favoriteRepository.save(entity);
        return favoriteMapper.entityToDTO(entity);
    }

    @Override
    @Transactional
    public Boolean deleteByUUID(UUID postId) {
        PostEntity postEntity = postRepository.findByUuid(postId).orElse(null);
        if (postEntity != null) {
            UserEntity currentUser = userContextService.getRequiredAuthenticatedUser();
            int deletedCount = favoriteRepository.deleteByPostAndUser(postEntity, currentUser);
            if (deletedCount > 0) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else {
            return Boolean.FALSE;
        }
    }
}
