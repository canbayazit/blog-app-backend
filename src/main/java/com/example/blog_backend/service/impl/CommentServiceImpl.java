package com.example.blog_backend.service.impl;

import com.example.blog_backend.core.service.impl.BaseServiceImpl;
import com.example.blog_backend.entity.CommentEntity;
import com.example.blog_backend.mapper.CommentMapper;
import com.example.blog_backend.model.requestDTO.CommentRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentResponseDTO;
import com.example.blog_backend.repository.CommentRepository;
import com.example.blog_backend.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl extends BaseServiceImpl<
        CommentEntity,
        CommentResponseDTO,
        CommentRequestDTO,
        CommentMapper,
        CommentRepository>
        implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    public CommentServiceImpl(CommentMapper commentMapper, CommentRepository commentRepository) {
        super(commentMapper, commentRepository);
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentResponseDTO> getAllCommentsByPostUuid(UUID postId) {
        return commentMapper.entityListToDTOList(commentRepository.findAllByPostUuid(postId));
    }
}
