package com.example.mnraderbackend.domain.user.repository;

import com.example.mnraderbackend.common.model.Animal;
import com.example.mnraderbackend.common.model.Scrap;
import com.example.mnraderbackend.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    @Query("select s.animal from Scrap s where s.user = :user")
    List<Animal> findAnimalsByUser(@Param("user") User user);

}
