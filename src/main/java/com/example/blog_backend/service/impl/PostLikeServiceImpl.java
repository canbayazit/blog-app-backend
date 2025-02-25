package com.example.blog_backend.service.impl;

import com.example.blog_backend.core.service.impl.AbstractBaseCrudServiceImpl;
import com.example.blog_backend.design.event.LikeCreatedEvent;
import com.example.blog_backend.design.event.LikeRemovedEvent;
import com.example.blog_backend.entity.PostEntity;
import com.example.blog_backend.entity.PostLikeEntity;
import com.example.blog_backend.entity.ReactionTypeEntity;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.mapper.PostLikeMapper;
import com.example.blog_backend.model.enums.ContextType;
import com.example.blog_backend.model.requestDTO.PostLikeRequestDTO;
import com.example.blog_backend.model.responseDTO.PostLikeResponseDTO;
import com.example.blog_backend.repository.PostLikeRepository;
import com.example.blog_backend.repository.PostRepository;
import com.example.blog_backend.repository.ReactionTypeRepository;
import com.example.blog_backend.service.PostLikeService;
import com.example.blog_backend.service.UserContextService;
import com.example.blog_backend.specification.PostLikeSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PostLikeServiceImpl extends AbstractBaseCrudServiceImpl<
        PostLikeEntity,
        PostLikeResponseDTO,
        PostLikeRequestDTO,
        PostLikeMapper,
        PostLikeRepository,
        PostLikeSpecification>
        implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final PostLikeMapper postLikeMapper;
    private final UserContextService userContextService;
    private final ApplicationEventPublisher eventPublisher;
    private final ReactionTypeRepository reactionTypeRepository;


    public PostLikeServiceImpl(PostLikeMapper postLikeMapper, PostLikeRepository postLikeRepository,
                               PostLikeSpecification postLikeSpecification, UserContextService userContextService,
                               PostRepository postRepository, ApplicationEventPublisher eventPublisher,
                               ReactionTypeRepository reactionTypeRepository) {
        super(postLikeMapper, postLikeRepository, postLikeSpecification, userContextService);
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
        this.postLikeMapper = postLikeMapper;
        this.userContextService = userContextService;
        this.eventPublisher = eventPublisher;
        this.reactionTypeRepository = reactionTypeRepository;

    }

    @Override
    @Transactional
    public PostLikeResponseDTO save(PostLikeRequestDTO requestDTO) {
        PostEntity postEntity = postRepository.findByUuid(requestDTO.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found."));
        ReactionTypeEntity reactionTypeEntity = reactionTypeRepository.findByName(requestDTO.getReactionType())
                .orElseThrow(() -> new EntityNotFoundException("Reaction type not found."));
        UserEntity currentUser = userContextService.getRequiredAuthenticatedUser();
        PostLikeEntity entity = postLikeMapper.requestDTOToEntity(requestDTO, postEntity, reactionTypeEntity, currentUser);
        postLikeRepository.save(entity);
        eventPublisher.publishEvent(new LikeCreatedEvent(this, postEntity.getId(), ContextType.POST, 1));
        return postLikeMapper.entityToDTO(entity);
    }

    @Override
    @Transactional
    public Boolean deleteByUUID(UUID postId) {
        PostEntity postEntity = postRepository.findByUuid(postId).orElse(null);
        if (postEntity != null) {
            UserEntity currentUser = userContextService.getRequiredAuthenticatedUser();
            int deletedCount = postLikeRepository.deleteByPostAndUser(postEntity, currentUser);
            if (deletedCount > 0) {
                eventPublisher.publishEvent(new LikeRemovedEvent(this, postEntity.getId(), ContextType.POST, 1));
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else {
            return Boolean.FALSE;
        }
    }

}
