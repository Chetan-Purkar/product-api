package com.chetan.productapi.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(name = "created_by", updatable = false)
    protected String createdBy;

    @Column(name = "created_on", updatable = false)
    protected LocalDateTime createdOn;

    @Column(name = "modified_by")
    protected String modifiedBy;

    @Column(name = "modified_on")
    protected LocalDateTime modifiedOn;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdOn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedOn = LocalDateTime.now();
    }
}
