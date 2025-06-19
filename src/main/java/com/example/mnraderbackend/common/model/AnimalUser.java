package com.example.mnraderbackend.common.model;

import com.example.mnraderbackend.common.convert.gender.Gender;
import com.example.mnraderbackend.common.convert.gender.GenderConverter;
import com.example.mnraderbackend.common.convert.status.Status;
import com.example.mnraderbackend.common.convert.status.StatusConverter;
import com.example.mnraderbackend.common.convert.statusAnimal.StatusAnimal;
import com.example.mnraderbackend.common.convert.statusAnimal.StatusAnimalConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class AnimalUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id", nullable = false)
    private Breed breed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Convert(converter = StatusAnimalConverter.class)
    private StatusAnimal status; // LOST(1), PROTECTED(2), SIGHTING(3)

    @CreatedDate
    @Column(nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Timestamp updatedAt;

    @Column(nullable = false)
    @Convert(converter = GenderConverter.class)
    private Gender gender; // MALE(1), FEMALE(2), UNKNOWN(3)

    @Column(columnDefinition = "TEXT")
    private String detail;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false)
    private int age;

    @Column(length = 40, nullable = false)
    private String name;
}