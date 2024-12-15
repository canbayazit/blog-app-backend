package com.example.blog_backend.service.impl;

import com.example.blog_backend.core.service.impl.BaseServiceImpl;
import com.example.blog_backend.entity.PostEntity;
import com.example.blog_backend.mapper.PostMapper;
import com.example.blog_backend.model.enums.PostStatus;
import com.example.blog_backend.model.requestDTO.PostRequestDTO;
import com.example.blog_backend.model.responseDTO.PostResponseDTO;
import com.example.blog_backend.repository.CategoryRepository;
import com.example.blog_backend.repository.PostRepository;
import com.example.blog_backend.service.PostService;
import com.example.blog_backend.service.UserContextService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PostServiceImpl extends BaseServiceImpl<
        PostEntity,
        PostResponseDTO,
        PostRequestDTO,
        PostMapper,
        PostRepository>
        implements PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final PostMapper postMapper;
    private final UserContextService userContextService;

    public PostServiceImpl(PostMapper postMapper, PostRepository postRepository, CategoryRepository categoryRepository,
                           UserContextService userContextService) {
        super(postMapper, postRepository);
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
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
