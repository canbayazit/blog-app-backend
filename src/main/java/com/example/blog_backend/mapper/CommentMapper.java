package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.*;
import com.example.blog_backend.model.requestDTO.CommentChildRequestDTO;
import com.example.blog_backend.model.requestDTO.CommentRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UserMapper.class, CommentAggregateMapper.class})
public interface CommentMapper extends IBaseMapper<CommentDTO, CommentEntity, CommentRequestDTO> {
    @Override
    default CommentEntity requestDTOToEntity(CommentRequestDTO dto) {
        return null;
    }

    // parent comment için
    @Mapping(target = "user", expression = "java(userEntity)")
    @Mapping(target = "post", expression = "java(postEntity)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    CommentEntity requestDTOToEntity(CommentRequestDTO requestDTO,
                                     @Context PostEntity postEntity,
                                     @Context UserEntity userEntity);

    @AfterMapping
    default void afterRequestDTOToEntity(CommentRequestDTO requestDTO,
                                        @Context PostEntity postEntity,
                                        @Context UserEntity userEntity,
                                        @MappingTarget CommentEntity commentEntity) {
        if (commentEntity.getStatistics() != null) {
            commentEntity.getStatistics().setComment(commentEntity);
        }
    }

    // burda context kullanmamızın nedeni mapstruct birden fazla parametre geçince hepsini kaynak olarak algılıyor
    // o yüzden bütün parametrelerdeki değerleri comment entity'ye maplemeye çalışıyor.
    // Context diyerek mapstruct'a  “Bu parametre, ek bir bağlam” (context) taşıyor. Yani “tüm alanları hedef nesneye
    // otomatik maplemek istemiyorum, ama bu parametreyi metot içinde kullanacağım/iletmek istiyorum” demektir.
    // @Context diyerek MapStruct’a “parentComment ve currentUser parametreleri birer bağlam (context) objesidir;
    // normal otomatik mapleme kaynağı değiller, ben explicit @Mapping(source=...) ile neyi nasıl maplemek istediğimi
    // söyleyeceğim” demiş olursun.
    @Mapping(target = "parentComment", expression = "java(parentComment)")
    @Mapping(target = "post", expression = "java(parentComment.getPost())")
    @Mapping(target = "user", expression = "java(currentUser)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    CommentEntity requestDTOToChildCommentEntity(CommentChildRequestDTO dto,
                                                                 @Context CommentEntity parentComment,
                                                                 @Context UserEntity currentUser);

    @AfterMapping
    default void afterRequestDTOToChildCommentEntity(CommentRequestDTO dto,
                                                     @Context CommentEntity parentComment,
                                                     @Context UserEntity currentUser,
                                                     @MappingTarget CommentEntity commentEntity) {
        if (commentEntity.getStatistics() != null) {
            commentEntity.getStatistics().setComment(commentEntity);
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "statistics", ignore = true)
    CommentEntity requestDtoToExistEntity(@MappingTarget CommentEntity entity, CommentRequestDTO requestDTO);

    @Mapping(target = "postId", source = "post.uuid")
    @Mapping(target = "parentCommentId", source = "parentComment.uuid")
    CommentDTO entityToDTO(CommentEntity commentEntity);
}
