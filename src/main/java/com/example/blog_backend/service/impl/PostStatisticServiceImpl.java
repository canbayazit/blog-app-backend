package com.example.blog_backend.service.impl;

import com.example.blog_backend.core.service.impl.AbstractBaseCrudServiceImpl;
import com.example.blog_backend.entity.PostStatisticEntity;
import com.example.blog_backend.mapper.PostStatisticMapper;
import com.example.blog_backend.model.requestDTO.PostStatisticRequestDTO;
import com.example.blog_backend.model.responseDTO.PostStatisticResponseDTO;
import com.example.blog_backend.repository.PostStatisticRepository;
import com.example.blog_backend.service.PostStatisticService;
import com.example.blog_backend.service.UserContextService;
import com.example.blog_backend.specification.PostStatisticSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostStatisticServiceImpl extends AbstractBaseCrudServiceImpl<
        PostStatisticEntity,
        PostStatisticResponseDTO,
        PostStatisticRequestDTO,
        PostStatisticMapper,
        PostStatisticRepository,
        PostStatisticSpecification>
        implements PostStatisticService {

    private final PostStatisticRepository postStatisticRepository;
    public PostStatisticServiceImpl(PostStatisticMapper postStatisticMapper,
                                    PostStatisticRepository postStatisticRepository,
                                    PostStatisticSpecification postStatisticSpecification,
                                    UserContextService userContextService) {
        super(postStatisticMapper, postStatisticRepository, postStatisticSpecification, userContextService);
        this.postStatisticRepository = postStatisticRepository;
    }

    @Override
    @Transactional
    public void incrementPostCommentCount(Long postId, int inc) {
        postStatisticRepository.incrementCommentCount(postId, inc);
    }
    @Override
    @Transactional
    public void decrementPostCommentCount(Long postId, int dec) {
        postStatisticRepository.decrementCommentCount(postId, dec);
    }
    @Override
    @Transactional
    public void incrementPostLikeCount(Long postId, int inc) {
        postStatisticRepository.incrementPostLikeCount(postId, inc);
    }
    @Override
    @Transactional
    public void decrementPostLikeCount(Long postId, int dec) {
        postStatisticRepository.decrementPostLikeCount(postId, dec);
    }
}
