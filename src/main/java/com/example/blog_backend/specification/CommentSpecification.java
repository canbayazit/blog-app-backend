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

    @Override
    public Predicate toPredicate(Root<CommentEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria: criteriaList) {
            Predicate predicate = null;
            if (criteria.getOperation().equalsIgnoreCase(">")) {
                predicate = criteriaBuilder.greaterThan(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase("<")) {
                predicate = criteriaBuilder.lessThan(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase(">=")) {
                predicate = criteriaBuilder.greaterThanOrEqualTo(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
            } else if (criteria.getOperation().equalsIgnoreCase("<=")) {
                predicate = criteriaBuilder.lessThanOrEqualTo(
                        root.<String>get(criteria.getKey()), criteria.getValue().toString());
                // burda commentları çekerken comment entity'yi post entity ile birleştirip post uuid si şu olan commentları
                // getir demek için filtreleme kullandık. Böylece bir posta ait commentları böylece çekebiliriz.
            } else if (criteria.getOperation().equalsIgnoreCase("=")) {
                if (criteria.getKey().equals("post")){
                    // root burda prooductEntity,
                    // root.join("categoryList") diyerek ProductEntity'i CategoryEntity tablosuyla join et dedik
                    // birleştirilmiş tabloyu join değişkenine atadık.
                    // sonra birleştirilmiş tablodan name kolonunu al ve client'tan gelen value ile filtrele
                    // burda  name category entity'de tanımlı category'nin ismini tutan kolon ismi
                    // mesela teknoloji category'sinden productları getir demiş olacaz.

                    // Not: join.get dediğimizde ana tabloyu değil joinlediğimiz tabloyu yani comment entity'yi
                    // eğer root.get dersek ana tabloyu almış oluruz.
                    Join<CommentEntity, PostEntity> join = root.join("post");
                    predicate = criteriaBuilder.equal(join.get("uuid"), UUID.fromString(criteria.getValue().toString()));
                } else {
                    predicate = criteriaBuilder.equal(
                            root.<String>get(criteria.getKey()), criteria.getValue().toString()
                    );
                }
            } else if (criteria.getOperation().equalsIgnoreCase(":")) {
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    predicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.<String>get(criteria.getKey())), "%" +
                                    criteria.getValue().toString().toLowerCase() + "%"
                    );
                } else {
                    predicate = criteriaBuilder.equal(
                            root.<String>get(criteria.getKey()), criteria.getValue().toString());
                }
            } else {
                continue;
            }

            predicates.add(predicate);
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
