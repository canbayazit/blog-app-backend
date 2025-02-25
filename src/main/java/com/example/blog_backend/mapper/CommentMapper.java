package com.example.blog_backend.mapper;

import com.example.blog_backend.core.mapper.IBaseMapper;
import com.example.blog_backend.entity.CommentEntity;
import com.example.blog_backend.entity.PostEntity;
import com.example.blog_backend.entity.UserEntity;
import com.example.blog_backend.model.requestDTO.CommentChildRequestDTO;
import com.example.blog_backend.model.requestDTO.CommentRequestDTO;
import com.example.blog_backend.model.responseDTO.CommentResponseDTO;
import org.mapstruct.*;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {PostMapper.class})
public interface CommentMapper extends IBaseMapper<CommentResponseDTO, CommentEntity, CommentRequestDTO> {
    @Mapping(target = "user", expression = "java(userEntity)")
    @Mapping(target = "post", expression = "java(postEntity)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    CommentEntity requestDTOToEntity(CommentRequestDTO requestDTO,
                                     @Context PostEntity postEntity,
                                     @Context UserEntity userEntity);

    @Override
    default CommentEntity requestDTOToEntity(CommentRequestDTO dto) {
        return null;
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

    @Mapping(target = "repliedTo", source = "repliedTo.user.username")
    CommentResponseDTO entityToDTO(CommentEntity commentEntity);
    @Mapping(target = "repliedTo", ignore = true)
    CommentEntity dtoToEntity(CommentResponseDTO dto);

}
