package com.example.blog_backend.core.entity;

import com.example.blog_backend.util.uuid.GeneratedUuidValue;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.tuple.GenerationTiming;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.UUID;
@MappedSuperclass
@Data
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GeneratedUuidValue(timing = GenerationTiming.INSERT)
    @Column(updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @CreatedDate
    private Date creationDate;

    @LastModifiedDate
    private Date updatedDate;

    @CreatedBy
    private String createdBy;
    @PrePersist
    protected void onCreate(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "";
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            username="anonymous";
        }
        else {
            username =  authentication.getPrincipal().toString();
        }
        setCreatedBy(username);
    }
}
