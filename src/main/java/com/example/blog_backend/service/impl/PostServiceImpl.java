package com.example.blog_backend.service.impl;

import com.example.blog_backend.core.service.impl.AbstractBaseCrudServiceImpl;
import com.example.blog_backend.entity.*;
import com.example.blog_backend.mapper.PostMapper;
import com.example.blog_backend.model.enums.PostStatus;
import com.example.blog_backend.model.requestDTO.*;
import com.example.blog_backend.model.responseDTO.PostDTO;
import com.example.blog_backend.repository.CategoryRepository;
import com.example.blog_backend.repository.PostRepository;
import com.example.blog_backend.service.*;
import com.example.blog_backend.specification.PostSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl extends AbstractBaseCrudServiceImpl<
        PostEntity,
        PostDTO,
        PostRequestDTO,
        PostMapper,
        PostRepository,
        PostSpecification>
        implements PostService {
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final PostMapper postMapper;
    private final UserContextService userContextService;
    private final CategoryRepository categoryRepository;


    public PostServiceImpl(PostMapper postMapper,
                           PostRepository postRepository,
                           CategoryService categoryService,
                           PostSpecification postSpecification,
                           UserContextService userContextService,
                           CategoryRepository categoryRepository) {
        super(postMapper, postRepository, postSpecification);
        this.postRepository = postRepository;
        this.categoryService = categoryService;
        this.postMapper = postMapper;
        this.userContextService = userContextService;
        this.categoryRepository = categoryRepository;

    }

    // hibernate post request içinde post statistic değerlerini göndermezsek bile kendisi post statistic değerlerini
    // default olarak oluşturup post statistic tablosuna set etme işlemini yapmıyor mu ?
    // static boş object "{}" gönderirdiğimizde post_id null olarak setlendiği için hata fırlatıyor.
    // Ama static null gönderirsek post'u kaydediyor
    @Override
    @Transactional
    public PostDTO save(PostRequestDTO requestDTO) {
        PostEntity postEntity = postMapper.requestDTOToEntity(
                requestDTO,
                userContextService.getRequiredAuthenticatedUser());
        for (CategoryRequestDTO category : requestDTO.getCategories()) {
            if (postEntity.getCategories() != null) {
                postEntity.getCategories().add(categoryRepository.findByUuid(category.getUuid())
                        .orElseThrow(() -> new EntityNotFoundException("Category not found!")));
            } else {
                Set<CategoryEntity> categories = new HashSet<>();
                categories.add(categoryRepository.findByUuid(category.getUuid())
                        .orElseThrow(() -> new EntityNotFoundException("Category not found!")));
                postEntity.setCategories(categories);
            }
        }
        return postMapper.entityToDTO(postRepository.save(postEntity));
    }

    @Override
    @Transactional
    public PostDTO update(UUID uuid, PostRequestDTO requestDTO) {
        PostEntity postEntity = postRepository.findByUuid(uuid).orElse(null);
        if (postEntity != null) {
            if (!postEntity.getStatus().equals(PostStatus.DRAFT)) {
                throw new IllegalStateException("Only draft posts can be updated");
            }
            postEntity = postMapper.requestDtoToExistEntity(postEntity, requestDTO);
            for (CategoryRequestDTO category : requestDTO.getCategories()) {
                if (postEntity.getCategories() != null){
                    postEntity.getCategories().clear();
                    CategoryEntity categoryEntity = categoryRepository.findByUuid(category.getUuid())
                            .orElseThrow(() -> new EntityNotFoundException("Category not found!"));
                    postEntity.getCategories().add(categoryEntity);
                } else {
                    Set<CategoryEntity> categories = new HashSet<>();
                    categories.add(categoryRepository.findByUuid(category.getUuid())
                            .orElseThrow(() -> new EntityNotFoundException("Category not found!")));
                    postEntity.setCategories(categories);
                }
            }
            return postMapper.entityToDTO(postRepository.save(postEntity));
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public PostDTO sendPublishRequest(UUID postId) {
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
    public List<PostDTO> getMyPostsByStatus(PostStatusRequestDTO status) {
        UserEntity currentUser = userContextService.getRequiredAuthenticatedUser();
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
