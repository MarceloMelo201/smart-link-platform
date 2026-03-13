package com.mm.smart_link_platform.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "links")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID linkId;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "short_code", nullable = false, unique = true)
    private String shortCode;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "access_count")
    private int accessCount = 0;

    public void increaseCount(){
        this.accessCount += 1;
    }

    public boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }

}
