package com.example.mnraderbackend.repository;

import com.example.mnraderbackend.common.model.AnimalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalUserRepository extends JpaRepository<AnimalUser, Long> {
}
