package com.example.blog_backend.entity;

import com.example.blog_backend.core.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "refresh_token")
public class RefreshTokenEntity extends BaseEntity {
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(nullable = false)
    private UserEntity user;
}
