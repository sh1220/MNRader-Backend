package com.example.mnraderbackend.domain.animal;

import com.example.mnraderbackend.common.model.Animal;
import com.example.mnraderbackend.common.model.Region;
import com.example.mnraderbackend.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Optional<Animal> findTopByOrderByIdDesc();
    List<Animal> findByRegion(Region region);
    List<Animal> findByRegionAndIdGreaterThanOrderByIdDesc(Region region, Long id);
    List<Animal> findByUser(User user);
}


