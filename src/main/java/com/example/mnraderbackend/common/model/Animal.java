package com.example.mnraderbackend.common.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Animal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "breed_id", nullable = false)
    private Breed breed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(nullable = false)
    private Integer status;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp updatedAt;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(nullable = false, length = 300)
    private String addressDetail;

    private Integer gender;

    private Timestamp when;

    @Column(nullable = false, length = 30)
    private String phone;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String detail;

    @Column(columnDefinition = "TEXT")
    private String image;
}