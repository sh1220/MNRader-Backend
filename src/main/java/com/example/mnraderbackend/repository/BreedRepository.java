package com.example.mnraderbackend.repository;

import com.example.mnraderbackend.common.convert.animal_type.AnimalType;
import com.example.mnraderbackend.common.model.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BreedRepository extends JpaRepository<Breed, Long> {
    Optional<Breed> findByBreed(String name);
    Optional<Breed> findByAnimalTypeAndBreed(AnimalType animalType, String breed);

}
