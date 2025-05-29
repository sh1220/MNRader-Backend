package com.example.mnraderbackend.common.model;

import com.example.mnraderbackend.common.convert.status.Status;
import com.example.mnraderbackend.common.convert.status.StatusConverter;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp updatedAt;

    @Column(nullable = false, length = 254)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;
}