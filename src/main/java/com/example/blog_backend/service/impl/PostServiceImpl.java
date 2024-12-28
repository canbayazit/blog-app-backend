package com.example.blog_backend.service.impl;

import com.example.blog_backend.core.service.impl.AbstractBaseCrudServiceImpl;
import com.example.blog_backend.entity.CategoryEntity;
import com.example.blog_backend.entity.PostEntity;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.mapper.PostMapper;
import com.example.blog_backend.model.enums.PostStatus;
import com.example.blog_backend.model.requestDTO.PostRequestDTO;
import com.example.blog_backend.model.requestDTO.PostStatusRequestDTO;
import com.example.blog_backend.model.responseDTO.PostResponseDTO;
import com.example.blog_backend.repository.PostRepository;
import com.example.blog_backend.service.CategoryService;
import com.example.blog_backend.service.PostService;
import com.example.blog_backend.service.UserContextService;
import com.example.blog_backend.specification.PostSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl extends AbstractBaseCrudServiceImpl<
        PostEntity,
        PostResponseDTO,
        PostRequestDTO,
        PostMapper,
        PostRepository,
        PostSpecification>
        implements PostService {
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final PostMapper postMapper;
    private final UserContextService userContextService;

    public PostServiceImpl(PostMapper postMapper, PostRepository postRepository, CategoryService categoryService,
                           PostSpecification postSpecification, UserContextService userContextService) {
        super(postMapper, postRepository, postSpecification);
        this.postRepository = postRepository;
        this.categoryService = categoryService;
        this.postMapper = postMapper;
        this.userContextService = userContextService;
    }

    @Override
    @Transactional
    public PostResponseDTO update(UUID uuid, PostRequestDTO requestDTO) {
        PostEntity postEntity = postRepository.findByUuid(uuid).orElse(null);
        if (postEntity != null) {
            if (!postEntity.getStatus().equals(PostStatus.DRAFT)) {
                throw new IllegalStateException("Only draft posts can be updated");
            }
            postEntity = postMapper.requestDtoToExistEntity(postEntity, requestDTO);
            postRepository.save(postEntity);
            return postMapper.entityToDTO(postEntity);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public PostResponseDTO sendPublishRequest(UUID postId) {
        PostEntity postEntity = postRepository.findByUuid(postId).orElse(null);
        if (postEntity != null) {
            if (!postEntity.getStatus().equals(PostStatus.DRAFT)) {
                throw new IllegalStateException("Only draft posts can be sent for review");
            }
            if (postEntity.getCategories() == null || postEntity.getCategories().isEmpty()) {
                throw new IllegalArgumentException("Post must have at least one valid category");
            }
            Set<UUID> categoryUuids = postEntity.getCategories()
                    .stream()
                    .map(CategoryEntity::getUuid)
                    .collect(Collectors.toSet());
            if (categoryService.countCategoryByUuids(categoryUuids) != postEntity.getCategories().size()) {
                throw new IllegalArgumentException("Some categories are invalid");
            }
            postEntity.setStatus(PostStatus.PENDING_REVIEW);
            postRepository.save(postEntity);
            return postMapper.entityToDTO(postEntity);
        } else {
            return null;
        }
    }

    @Override
    public List<PostResponseDTO> getMyPostsByStatus(PostStatusRequestDTO status) {
        UserEntity currentUser = userContextService.getCurrentAuthenticatedUser();
        List<PostEntity> drafts = postRepository.findByUserAndStatus(currentUser, PostStatus.valueOf(status.getPostStatus()));
        return postMapper.entityListToDTOList(drafts);
    }

    //NOT: many to many ilişkilerde hibernate her bir bağlantı için ayrı ayrı sql sorgusu oluşturur ve buda performans
    // sorunu yaratabilir. Bu gibi durumlarda delete işlemi için aşağıdaki gibi Bulk Remove yani tek bir SQL Delete
    // sorgusu yazarak performans iyileştirmesi yapılması önerilir ve cascade.REMOVE kullanılması önerilmez.
    // Fakat ben uygulamam çok geniş bir veriye sahip olmayacağı için uğraşmıyorum ama ne olur ne olmaz diye belki
    // ilerde lazım olur diye burda bırakıyorum. Kod yapısı deneyseldir.

    /*@Transactional
    @Override
    public Boolean deleteByUUID(UUID uuid) {
        PostEntity post = entityManager.createQuery("SELECT p FROM PostEntity p WHERE p.uuid = :uuid", PostEntity.class)
                .setParameter("uuid", uuid)
                .getSingleResult();

        if (post != null) {
            // İlişki tablosundaki tüm bağlantıları topluca kaldır
            entityManager.createNativeQuery("DELETE FROM posts_categories WHERE post_id = :postId")
                    .setParameter("postId", post.getId())
                    .executeUpdate();

            // Ardından `PostEntity`'yi sil
            entityManager.remove(post);
            return true;
        }
        return false;
    }*/
}
