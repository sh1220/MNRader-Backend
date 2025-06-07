package com.example.mnraderbackend.repository;

import com.example.mnraderbackend.common.model.AnimalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalUserRepository extends JpaRepository<AnimalUser, Long> {
    List<AnimalUser> findByUserEmail(String email);
}
