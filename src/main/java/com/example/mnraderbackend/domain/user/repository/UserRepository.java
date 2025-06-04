package com.example.mnraderbackend.domain.user.repository;

import com.example.mnraderbackend.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 1. Region ID로 조회하는 버전 (옵션)
    @Query("SELECT u.fcmToken FROM User u WHERE u.region.id = :regionId AND u.fcmToken IS NOT NULL")
    List<String> findFcmTokensByRegionId(@Param("regionId") Long regionId);
}
