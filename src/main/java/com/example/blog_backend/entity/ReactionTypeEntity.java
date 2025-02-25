package com.example.blog_backend.entity;

import com.example.blog_backend.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reaction_types")
public class ReactionTypeEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
}
