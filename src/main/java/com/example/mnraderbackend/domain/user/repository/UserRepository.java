package com.example.mnraderbackend.domain.user.repository;

import com.example.mnraderbackend.common.model.Region;
import com.example.mnraderbackend.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 1. Region ID로 조회하는 버전 (옵션)
    @Query("SELECT u.fcmToken FROM User u WHERE u.region = :region AND u.fcmToken IS NOT NULL")
    List<String> findFcmTokensByRegion(@Param("region") Region region);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}
