package com.example.blog_backend.service.impl;

import com.example.blog_backend.core.service.impl.AbstractBaseCrudServiceImpl;
import com.example.blog_backend.design.event.LikeCreatedEvent;
import com.example.blog_backend.design.event.LikeRemovedEvent;
import com.example.blog_backend.entity.*;
import com.example.blog_backend.mapper.CommentLikeMapper;
import com.example.blog_backend.model.enums.ContextType;
import com.example.blog_backend.model.requestDTO.CommentLikeRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentLikeDTO;
import com.example.blog_backend.repository.CommentLikeRepository;
import com.example.blog_backend.repository.CommentRepository;
import com.example.blog_backend.repository.ReactionTypeRepository;
import com.example.blog_backend.service.CommentLikeService;
import com.example.blog_backend.service.UserContextService;
import com.example.blog_backend.specification.CommentLikeSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CommentLikeServiceImpl extends AbstractBaseCrudServiceImpl<
        CommentLikeEntity,
        CommentLikeDTO,
        CommentLikeRequestDTO,
        CommentLikeMapper,
        CommentLikeRepository,
        CommentLikeSpecification>
        implements CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeMapper commentLikeMapper;
    private final UserContextService userContextService;
    private final ReactionTypeRepository reactionTypeRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CommentLikeServiceImpl(CommentLikeMapper commentLikeMapper,
                                  CommentLikeRepository commentLikeRepository,
                                  CommentLikeSpecification commentLikeSpecification,
                                  UserContextService userContextService,
                                  CommentRepository commentRepository,
                                  ReactionTypeRepository reactionTypeRepository,
                                  ApplicationEventPublisher eventPublisher) {
        super(commentLikeMapper, commentLikeRepository, commentLikeSpecification);
        this.commentLikeRepository = commentLikeRepository;
        this.commentRepository = commentRepository;
        this.commentLikeMapper = commentLikeMapper;
        this.userContextService = userContextService;
        this.reactionTypeRepository = reactionTypeRepository;
        this.eventPublisher = eventPublisher;
    }


    @Override
    @Transactional
    public CommentLikeDTO save(CommentLikeRequestDTO requestDTO) {
        CommentEntity commentEntity = commentRepository.findByUuid(requestDTO.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException("Comment not found."));
        ReactionTypeEntity reactionTypeEntity = reactionTypeRepository.findByName(requestDTO.getReactionType().getName())
                .orElseThrow(() -> new EntityNotFoundException("Reaction type not found."));
        UserEntity currentUser = userContextService.getRequiredAuthenticatedUser();
        CommentLikeEntity entity = commentLikeMapper.requestDTOToEntity(requestDTO, commentEntity, reactionTypeEntity, currentUser);
        commentLikeRepository.save(entity);
        eventPublisher.publishEvent(new LikeCreatedEvent(this, commentEntity.getId(), ContextType.COMMENT, 1));
        return commentLikeMapper.entityToDTO(entity);
    }

    @Override
    @Transactional
    public Boolean deleteByUUID(UUID commentId) {
        CommentEntity commentEntity = commentRepository.findByUuid(commentId).orElse(null);
        if (commentEntity != null) {
            UserEntity currentUser = userContextService.getRequiredAuthenticatedUser();
            int deletedCount = commentLikeRepository.deleteByCommentAndUser(commentEntity, currentUser);
            if (deletedCount > 0) {
                eventPublisher.publishEvent(new LikeRemovedEvent(this, commentEntity.getId(), ContextType.COMMENT, 1));
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else {
            return Boolean.FALSE;
        }
    }
}
