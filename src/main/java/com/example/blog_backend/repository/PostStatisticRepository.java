package com.example.blog_backend.repository;

import com.example.blog_backend.core.repository.BaseRepository;
import com.example.blog_backend.entity.PostStatisticEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
/*
Neden Custom SQL?
        Eğer önce entity'yi çekip, örneğin commentAggregateEntity.setLikeCount(newValue) yapıp sonra kaydetseniz,
        şu problem ortaya çıkar:

        İki işlem aynı anda veritabanındaki değeri okur (örneğin her ikisi de 10 değeriyle başlar).
        Her işlem, kendi okuduğu değerin üzerine artışı uygular (10 + 1 = 11).
        Sonuçta iki işlem de 11 değerini yazar ve gerçek artış 2 yerine 1 olmuş olur.

        Bu tür durumlar "lost update" (kayıp güncelleme) sorunlarına yol açar.

        Custom SQL (Atomic Increment) ile sorgu, güncelleme işlemini veritabanı düzeyinde tek seferde yapar. Yani,
        her işlem kendi artışını uygular ve diğer işlemler bekletilerek, güncel değer üzerinden artış sağlanır.
 */
@Repository
public interface PostStatisticRepository extends BaseRepository<PostStatisticEntity> {
    @Modifying
    @Query("UPDATE PostStatisticEntity p SET p.commentCount = p.commentCount + :inc WHERE p.post.id = :postId")
    void incrementCommentCount(@Param("postId") Long postId, @Param("inc") int inc);

    @Modifying
    @Query("UPDATE PostStatisticEntity p SET p.commentCount = p.commentCount - :dec WHERE p.post.id = :postId")
    void decrementCommentCount(@Param("postId") Long postId, @Param("dec") int dec);

    @Modifying
    @Query("UPDATE PostStatisticEntity p SET p.likeCount = p.likeCount + :inc WHERE p.post.id = :postId")
    void incrementPostLikeCount(@Param("postId") Long postId, @Param("inc") int inc);

    @Modifying
    @Query("UPDATE PostStatisticEntity p SET p.likeCount = p.likeCount - :dec WHERE p.post.id = :postId")
    void decrementPostLikeCount(@Param("postId") Long postId, @Param("dec") int dec);
}
