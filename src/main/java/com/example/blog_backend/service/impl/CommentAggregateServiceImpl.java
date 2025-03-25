package com.example.blog_backend.service.impl;

import com.example.blog_backend.core.service.impl.AbstractBaseCrudServiceImpl;
import com.example.blog_backend.entity.CommentAggregateEntity;
import com.example.blog_backend.mapper.CommentAggregateMapper;
import com.example.blog_backend.model.requestDTO.CommentAggregateRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentAggregateDTO;
import com.example.blog_backend.repository.CommentAggregateRepository;
import com.example.blog_backend.service.CommentAggregateService;
import com.example.blog_backend.specification.CommentAggregateSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentAggregateServiceImpl extends AbstractBaseCrudServiceImpl<
        CommentAggregateEntity,
        CommentAggregateDTO,
        CommentAggregateRequestDTO,
        CommentAggregateMapper,
        CommentAggregateRepository,
        CommentAggregateSpecification>
        implements CommentAggregateService {

    private final CommentAggregateRepository commentAggregateRepository;

    public CommentAggregateServiceImpl(CommentAggregateMapper commentAggregateMapper,
                                       CommentAggregateRepository commentAggregateRepository,
                                       CommentAggregateSpecification commentAggregateSpecification) {
        super(commentAggregateMapper, commentAggregateRepository, commentAggregateSpecification);
        this.commentAggregateRepository = commentAggregateRepository;
    }

    @Override
    @Transactional
    public void incrementCommentLikeCount(Long commentId, int inc) {
        commentAggregateRepository.incrementCommentLikeCount(commentId, inc);
    }
    @Override
    @Transactional
    public void decrementCommentLikeCount(Long commentId, int dec) {
        commentAggregateRepository.decrementCommentLikeCount(commentId, dec);
    }
    @Override
    @Transactional
    public void incrementChildCount(Long commentId, int inc) {
        commentAggregateRepository.incrementChildCount(commentId, inc);
    }
    @Override
    @Transactional
    public void decrementChildCount(Long commentId, int dec) {
        commentAggregateRepository.decrementChildCount(commentId, dec);
    }
}
