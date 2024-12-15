package com.example.blog_backend.service.impl;

import com.example.blog_backend.core.service.impl.BaseServiceImpl;
import com.example.blog_backend.entity.CommentEntity;
import com.example.blog_backend.mapper.CommentMapper;
import com.example.blog_backend.model.requestDTO.CommentRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentResponseDTO;
import com.example.blog_backend.repository.CommentRepository;
import com.example.blog_backend.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends BaseServiceImpl<
        CommentEntity,
        CommentResponseDTO,
        CommentRequestDTO,
        CommentMapper,
        CommentRepository>
        implements CommentService {

    public CommentServiceImpl(CommentMapper commentMapper, CommentRepository commentRepository) {
        super(commentMapper, commentRepository);
    }
}
