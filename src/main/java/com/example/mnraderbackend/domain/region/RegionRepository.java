package com.example.mnraderbackend.domain.region;

import com.example.mnraderbackend.common.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RegionRepository extends JpaRepository<Region, Long> {
}


