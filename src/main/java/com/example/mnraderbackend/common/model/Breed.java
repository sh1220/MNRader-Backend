package com.example.mnraderbackend.common.model;

import com.example.mnraderbackend.common.convert.animal.AnimalConverter;
import com.example.mnraderbackend.common.convert.status.Status;
import com.example.mnraderbackend.common.convert.status.StatusConverter;
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
    @Convert(converter = AnimalConverter.class)
    private Animal animal;

    @Column(nullable = false, length = 50)
    private String breed;
}