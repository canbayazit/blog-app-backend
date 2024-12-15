package com.example.blog_backend.entity;

import com.example.blog_backend.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

// hashmap, hashset gibi bir entity'yi diğeriyle kıyaslarken kullanılan Equals ve HashCode bidirectional ilişkilerde
// döngüsel sorunlar yaratıyor bu yüzden ilişkinin birinden bu iki entity'yi kıyaslarken ilgili ilişkiyi exclude etmemiz
// gerekir döngüsel hatayı engellemek için. Burda Kategorileri birbirleriyle kıyaslarken post'a gerek yok dedik ve posts
// ilişkisini karşılaştırmadan çıkardık.
@Getter
@Setter
@Entity
@Table(name = "categories")
@EqualsAndHashCode(exclude = {"posts"}, callSuper = true)
@ToString(exclude = {"posts"})
public class CategoryEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    // çift yönlü ilişkilerde mappedBy tanımlamazsak jpa kimin ilişkiyi yönettğini anlamaz ve
    // iki tane ortak tablo oluşturur posts-categories ile categories-posts diye
    @ManyToMany(mappedBy = "categories")
    private Set<PostEntity> posts = new HashSet<>();
}
