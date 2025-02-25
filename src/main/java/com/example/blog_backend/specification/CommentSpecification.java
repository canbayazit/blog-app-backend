package com.example.blog_backend.specification;

import com.example.blog_backend.core.specification.BaseSpecification;
import com.example.blog_backend.core.specification.SearchCriteria;
import com.example.blog_backend.entity.CommentEntity;
import com.example.blog_backend.entity.PostEntity;
import jakarta.persistence.criteria.*;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Component
public class CommentSpecification extends BaseSpecification<CommentEntity> {
    private List<SearchCriteria> criteriaList;

    public void setCriteriaList(List<SearchCriteria> criteriaList) {
        this.criteriaList = criteriaList;
    }

    // commentlar için sadece post id göre commentları getirmeye ihtiyacım var. O yüzden diğer filtrelere şimdilik
    // ihtiyacım yok.
    @Override
    public Predicate toPredicate(Root<CommentEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria: criteriaList) {
            Predicate predicate = null;
            // burda commentları çekerken comment entity'yi post entity ile birleştirip post uuid si şu olan commentları
            // getir demek için filtreleme kullandık. Böylece bir posta ait commentları çekebiliriz.
            if (criteria.getOperation().equalsIgnoreCase("=")) {
                if (criteria.getKey().equals("post")){
                    // root burda CommentEntity,
                    // root.join("post") diyerek CommentEntity'i PostEntity tablosuyla join et dedik
                    // birleştirilen tabloyu join değişkenine atadık.
                    // Burda join böylece birleştirilen tablo olan Post Entity'yi  temsil etmiş oluyor.

                    // Not: join.get("uuid") dediğimizde Post Entity tablosundaki property'lere yani fieldlara bakar
                    // eğer root.get dersek ana tabloyu yani comment entity'deki değişkenleri almış oluruz.
                    // Joinlenmiş 2 tabloda filtreleme yaparken hangi tablonun değişkenine göre filtreleme yapacağımız
                    // bu şekilde ayırt edebiliriz.
                    Join<CommentEntity, PostEntity> join = root.join("post");
                    predicate = criteriaBuilder.equal(join.get("uuid"), UUID.fromString(criteria.getValue().toString()));
                } else if (criteria.getKey().equals("parentComment")) {
                    // Eğer gelen değer "null" ise, ana yorumları getir:
                    if (criteria.getValue() == null) {
                        predicate = criteriaBuilder.isNull(root.get("parentComment"));
                    } else {
                        // Aksi halde, belirli bir parent yorumun child yorumlarını getir.
                        predicate = criteriaBuilder.equal(root.get("parentComment").get("uuid"), UUID.fromString(criteria.getValue().toString()));
                    }
                }  else {
                    predicate = criteriaBuilder.equal(
                            root.<String>get(criteria.getKey()), criteria.getValue().toString()
                    );
                }
            } else {
                continue;
            }

            predicates.add(predicate);
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
