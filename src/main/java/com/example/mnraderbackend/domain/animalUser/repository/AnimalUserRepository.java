package com.example.mnraderbackend.domain.animalUser.repository;

import com.example.mnraderbackend.common.model.AnimalUser;
import com.example.mnraderbackend.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AnimalUserRepository extends JpaRepository<AnimalUser, Long> {
    List<AnimalUser> findByUser(User user);

}


