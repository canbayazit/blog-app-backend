package com.example.blog_backend.specification;

import com.example.blog_backend.core.specification.BaseSpecification;
import com.example.blog_backend.core.specification.SearchCriteria;
import com.example.blog_backend.entity.*;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FavoriteSpecification  extends BaseSpecification<FavoriteEntity> {
    private List<SearchCriteria> criteriaList;

    public void setCriteriaList(List<SearchCriteria> criteriaList) {
        this.criteriaList = criteriaList;
    }

    @Override
    public Predicate toPredicate(Root<FavoriteEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria: criteriaList) {
            Predicate predicate = null;
            if (criteria.getOperation().equalsIgnoreCase("=")) {
                if (criteria.getKey().equals("user")){
                    Join<FavoriteEntity, UserEntity> join = root.join("user");
                    predicate = criteriaBuilder.equal(join.get("uuid"), UUID.fromString(criteria.getValue().toString()));
                }
            } else {
                continue;
            }

            predicates.add(predicate);
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
