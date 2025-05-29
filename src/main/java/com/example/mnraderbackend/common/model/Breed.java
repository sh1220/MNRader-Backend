package com.example.mnraderbackend.common.model;

import com.example.mnraderbackend.common.convert.animal_type.AnimalType;
import com.example.mnraderbackend.common.convert.animal_type.AnimalTypeConverter;
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
    @Convert(converter = AnimalTypeConverter.class)
    private AnimalType animalType;

    @Column(nullable = false, length = 50)
    private String breed;
}