package com.example.mnraderbackend.common.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AnimalUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "breed_id", nullable = false)
    private Breed breed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer status; // LOST(1), PROTECTED(2), SIGHTING(3)

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp updatedAt;

    @Column(nullable = false)
    private Integer gender; // MALE(1), FEMALE(2), UNKNOWN(3)

    @Column(columnDefinition = "TEXT")
    private String detail;

    @Column(columnDefinition = "TEXT")
    private String image;
}