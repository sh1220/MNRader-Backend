package com.example.mnraderbackend.common.model;

import com.example.mnraderbackend.common.convert.status.Status;
import com.example.mnraderbackend.common.convert.status.StatusConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status; // ACTIVE(1), INACTIVE(2), SUSPEND(3)

    @CreatedDate
    @Column(nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Timestamp updatedAt;

    @Column(nullable = false, length = 254)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(length = 512)
    private String refreshToken;

    @Column(length = 512)
    private String fcmToken;
}