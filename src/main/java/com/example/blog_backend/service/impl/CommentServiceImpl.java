package com.example.blog_backend.service.impl;

import com.example.blog_backend.core.service.impl.AbstractBaseCrudServiceImpl;
import com.example.blog_backend.design.event.CommentCreatedEvent;
import com.example.blog_backend.design.event.CommentRemovedEvent;
import com.example.blog_backend.entity.*;
import com.example.blog_backend.mapper.CommentAggregateMapper;
import com.example.blog_backend.mapper.CommentMapper;
import com.example.blog_backend.model.dto.ReactionTypeCountDTO;
import com.example.blog_backend.model.requestDTO.*;
import com.example.blog_backend.model.responseDTO.CommentDTO;
import com.example.blog_backend.model.responseDTO.PageDTO;
import com.example.blog_backend.repository.*;
import com.example.blog_backend.service.CommentService;
import com.example.blog_backend.service.UserContextService;
import com.example.blog_backend.specification.CommentSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends AbstractBaseCrudServiceImpl<
        CommentEntity,
        CommentDTO,
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
        super(commentMapper, commentRepository, commentSpecification);
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

    // post'a ait parent yorumları veya parent yoruma ait child yorumları specification ile filtreleyerek getiren metot
    @Override
    public PageDTO<CommentDTO> getAllPageByFilter(BaseFilterRequestDTO filter) {
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
        PageDTO<CommentDTO> pageResponseDTO = commentMapper.pageEntityToPageDTO(entityPage);

        // Opsiyonel kullanıcı bilgisi; oturum açılmamışsa Optional.empty() dönecek.
        Optional<UserEntity> optionalUser = userContextService.getOptionalAuthenticatedUser();

        // DTO'lardaki comment UUID'lerini topla.
        List<CommentDTO> content = pageResponseDTO.getContent();
        List<UUID> commentUuids = content.stream()
                .map(CommentDTO::getUuid)
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

                // Her bir CommentDTO için, kullanıcıya özel reaction bilgisi ve toplam reaction count bilgisini set et.
                for (CommentDTO commentDTO : content) {
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
                for (CommentDTO commentDTO : content) {
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


    @Override
    @Transactional
    public CommentDTO save(CommentRequestDTO requestDTO) {
        PostEntity post = postRepository.findByUuid(requestDTO.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found."));
        UserEntity user = userContextService.getRequiredAuthenticatedUser();
        CommentEntity comment = commentMapper.requestDTOToEntity(requestDTO, post, user);
        CommentDTO commentResponseDTO = commentMapper.entityToDTO(commentRepository.save(comment));

        CommentCreatedEvent event = new CommentCreatedEvent(this,
                comment.getPost().getId(),
                comment.getId()
        );
        eventPublisher.publishEvent(event);

        return commentResponseDTO;
    }

    @Override
    @Transactional
    public CommentDTO createReply(UUID targetCommentUuid, CommentChildRequestDTO requestDTO) {
        // Yanıt verilecek yorumu alıyoruz. Bu parent comment'a cevapta olabilir child comment'a cevapta olabilir.
        CommentEntity targetComment = commentRepository.findByUuid(targetCommentUuid)
                .orElseThrow(() -> new EntityNotFoundException("Target comment not found."));

        // Child yorumun ana yorumunu belirleyelim:
        // bir kullanıcı bir ana yoruma veya ana yorum altındaki herhangi bri child comment'a yorum atabilir
        // Eğer targetComment bir ana yorum ise, targetComment ana yorum olur.
        // Eğer targetComment bir child ise, yani ana yorum altında bir child yoruma cevap verilmiş ise
        // o zaman targetComment'ın ana yorumunu almamız lazım oda şu şekilde targetComment.getParentComment() olur.
        CommentEntity parentComment = (targetComment.getParentComment() == null)
                ? targetComment
                : targetComment.getParentComment();

        // Yeni cevap (child comment) oluşturulurken, parent olarak  ana yorum olan parentComment'ı set ediyoruz.
        CommentEntity childComment = commentMapper.requestDTOToChildCommentEntity(
                requestDTO,
                parentComment,
                userContextService.getRequiredAuthenticatedUser());

        CommentDTO commentDTO = commentMapper.entityToDTO(commentRepository.save(childComment));

        CommentCreatedEvent event = new CommentCreatedEvent(this,
                childComment.getPost().getId(),
                childComment.getId());
        eventPublisher.publishEvent(event);

        return commentDTO;
    }


    @Override
    @Transactional
    public Boolean deleteByUUID(UUID uuid) {
        CommentEntity comment = commentRepository.findByUuid(uuid).orElse(null);
        if (comment == null) {
            return false;
        }

        // Kaç yorum silinecek (parent + child sayısı?)
        int totalDeleted = 1;

        // Child Yorumlar Var mı?
        // Tek seviye child. CascadeType.ALL ise, repository.delete(parent) çocukları da silecek.
        List<CommentEntity> replies = comment.getReplies();
        if (replies != null && !replies.isEmpty()) {
            // totalDeleted = 1 (kendisi) + replies.size()
            totalDeleted += replies.size();
        }

        CommentRemovedEvent event = new CommentRemovedEvent(this,
                comment.getPost().getId(),
                totalDeleted);
        eventPublisher.publishEvent(event);

        // 5) Yorumu Sil (Cascade sayesinde child'lar da otomatik silinir.)
        commentRepository.delete(comment);

        return true;
    }
}

