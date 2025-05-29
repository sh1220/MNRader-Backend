package com.example.mnraderbackend.common.model;

import com.example.mnraderbackend.common.convert.gender.Gender;
import com.example.mnraderbackend.common.convert.gender.GenderConverter;
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
    @Convert(converter = StatusConverter.class)
    private Status status; // LOST(1), PROTECTED(2), SIGHTING(3)

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp updatedAt;

    @Column(nullable = false)
    @Convert(converter = GenderConverter.class)
    private Gender gender; // MALE(1), FEMALE(2), UNKNOWN(3)

    @Column(columnDefinition = "TEXT")
    private String detail;

    @Column(columnDefinition = "TEXT")
    private String image;
}