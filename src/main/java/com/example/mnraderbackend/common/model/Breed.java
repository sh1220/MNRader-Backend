package com.example.mnraderbackend.common.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Breed {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer animal; // DOG(1), CAT(2), OTHER(3)

    @Column(nullable = false, length = 50)
    private String breed;
}