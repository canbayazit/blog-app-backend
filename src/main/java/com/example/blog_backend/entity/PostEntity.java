package com.example.blog_backend.entity;

import com.example.blog_backend.core.entity.BaseEntity;
import com.example.blog_backend.model.enums.PostStatus;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class PostEntity extends BaseEntity {

    @Column(nullable = false)
    private String title;

    // content'in çok uzun olabileceğinden TEXT türünde tanımladık
    // Bu sayede sınırlı karakter sayısına sahip VARCHAR yerine çok daha fazla veri saklayabiliriz.
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<CategoryEntity> categories = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> comments = new ArrayList<>();

    // Sadece basit türde veriler (ör. String, Integer, vb.) saklanacağı için yeni bir entity tanımlamadan
    // bu veriyi ilişkilendirmemizi sağlar. Entity Karmaşıklığını Azaltır
    @ElementCollection // basit türdeki veriler için ek bir tablo oluşturur
    @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tag")
    private List<String> tags;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private PostStatisticEntity statistics;

    @PrePersist
    protected void onCreate() {
        super.onCreate();
        status = PostStatus.DRAFT; // Başlangıçta taslak olarak kaydedilsin
    }
}
