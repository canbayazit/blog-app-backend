package com.example.blog_backend.service.impl;

import com.example.blog_backend.core.service.impl.AbstractBaseCrudServiceImpl;
import com.example.blog_backend.entity.*;
import com.example.blog_backend.mapper.CommentAggregateMapper;
import com.example.blog_backend.mapper.CommentMapper;
import com.example.blog_backend.model.dto.ReactionTypeCountDTO;
import com.example.blog_backend.model.requestDTO.*;
import com.example.blog_backend.model.responseDTO.CommentResponseDTO;
import com.example.blog_backend.model.responseDTO.PageResponseDTO;
import com.example.blog_backend.repository.*;
import com.example.blog_backend.service.CommentService;
import com.example.blog_backend.service.UserContextService;
import com.example.blog_backend.specification.CommentSpecification;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends AbstractBaseCrudServiceImpl<
        CommentEntity,
        CommentResponseDTO,
        CommentRequestDTO,
        CommentMapper,
        CommentRepository,
        CommentSpecification>
        implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentAggregateRepository commentAggregateRepository;
    private final CommentMapper commentMapper;
    private final CommentSpecification commentSpecification;
    private final UserContextService userContextService;
    private final ApplicationEventPublisher eventPublisher;
    private final CommentAggregateMapper commentAggregateMapper;
    private final CommentLikeRepository commentLikeRepository;

    public CommentServiceImpl(CommentMapper commentMapper,
                              CommentRepository commentRepository,
                              CommentSpecification commentSpecification,
                              UserContextService userContextService,
                              PostRepository postRepository,
                              CommentAggregateRepository commentAggregateRepository,
                              ApplicationEventPublisher eventPublisher,
                              CommentAggregateMapper commentAggregateMapper,
                              CommentLikeRepository commentLikeRepository) {
        super(commentMapper, commentRepository, commentSpecification, userContextService);
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentAggregateRepository = commentAggregateRepository;
        this.commentSpecification = commentSpecification;
        this.userContextService = userContextService;
        this.eventPublisher = eventPublisher;
        this.commentAggregateMapper = commentAggregateMapper;
        this.commentLikeRepository = commentLikeRepository;
    }

    @Override
    public PageResponseDTO<CommentResponseDTO> getAllPageByFilter(BaseFilterRequestDTO filter) {
        Pageable pageable;
        if (filter.getSortDTO() != null) {
            if (filter.getSortDTO().getDirection() == Sort.Direction.DESC) {
                pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize(),
                        Sort.by(filter.getSortDTO().getColumnName()).descending());
            } else {
                pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize(),
                        Sort.by(filter.getSortDTO().getColumnName()).ascending());
            }
        } else {
            pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize(),
                    Sort.by("id").ascending());
        }

        // Comment filter ve specification ayarları
        commentSpecification.setCriteriaList(filter.getFilters());
        Page<CommentEntity> entityPage = commentRepository.findAll(commentSpecification, pageable);
        PageResponseDTO<CommentResponseDTO> pageResponseDTO = commentMapper.pageEntityToPageDTO(entityPage);

        // Opsiyonel kullanıcı bilgisi; oturum açılmamışsa Optional.empty() dönecek.
        Optional<UserEntity> optionalUser = userContextService.getOptionalAuthenticatedUser();

        // DTO'lardaki comment UUID'lerini topla.
        List<CommentResponseDTO> content = pageResponseDTO.getContent();
        List<UUID> commentUuids = content.stream()
                .map(CommentResponseDTO::getUuid)
                .collect(Collectors.toList());

        if (!commentUuids.isEmpty()) {
            // A) Öncelikle, her yorum için reaction count bilgilerini çekelim.
            List<Object[]> reactionCountResults = commentLikeRepository.findReactionCountsByCommentUuids(commentUuids);
            Map<UUID, List<ReactionTypeCountDTO>> reactionCountMap = new HashMap<>();
            for (Object[] row : reactionCountResults) {
                UUID commentUuid = (UUID) row[0];
                String reactionTypeName = (String) row[1];
                long count = (Long) row[2];

                ReactionTypeCountDTO countDTO = new ReactionTypeCountDTO();
                countDTO.setReactionType(reactionTypeName);
                countDTO.setCount(count);

                reactionCountMap.computeIfAbsent(commentUuid, k -> new ArrayList<>()).add(countDTO);
            }

            // B) Eğer kullanıcı oturum açmışsa, kullanıcının yorumlara verdiği reaction bilgisini de çekelim.
            if (optionalUser.isPresent()) {
                UserEntity currentUser = optionalUser.get();
                List<Object[]> reactionResults = commentLikeRepository.findReactionTypesByUserAndCommentUuids(currentUser, commentUuids);
                Map<UUID, ReactionTypeEntity> reactionMap = reactionResults.stream()
                        .collect(Collectors.toMap(
                                row -> (UUID) row[0],
                                row -> (ReactionTypeEntity) row[1]
                        ));

                // Her bir CommentResponseDTO için, kullanıcıya özel reaction bilgisi ve toplam reaction count bilgisini set et.
                for (CommentResponseDTO commentDTO : content) {
                    ReactionTypeEntity reaction = reactionMap.get(commentDTO.getUuid());
                    boolean isLiked = reaction != null;
                    if (commentDTO.getStatistics() != null) {
                        commentDTO.getStatistics().setLiked(isLiked);
                        commentDTO.getStatistics().setReacted(isLiked ? reaction.getName() : null);
                        List<ReactionTypeCountDTO> counts = reactionCountMap.get(commentDTO.getUuid());
                        commentDTO.getStatistics().setReactionTypeCounts(counts);
                    }
                }
            } else {
                // Kullanıcı oturum açmamışsa, yalnızca toplam reaction count bilgilerini set et.
                for (CommentResponseDTO commentDTO : content) {
                    if (commentDTO.getStatistics() != null) {
                        commentDTO.getStatistics().setLiked(false);
                        commentDTO.getStatistics().setReacted(null);
                        List<ReactionTypeCountDTO> counts = reactionCountMap.get(commentDTO.getUuid());
                        commentDTO.getStatistics().setReactionTypeCounts(counts);
                    }
                }
            }
        }

        return pageResponseDTO;
    }

}
