package com.mm.smart_link_platform.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "access")
@Table(name = "access_log")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "link_id", nullable = false)
    private UUID linkId;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "user_agent", nullable = false)
    private String userAgent;

    @Column(name = "referer")
    private String referer;

    @CreationTimestamp
    @Column(name = "access_time", nullable = false)
    private LocalDateTime accessTime;
}
