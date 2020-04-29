package com.example.stations.model.repository;

import com.example.stations.model.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<StationEntity, Long> {

    StationEntity getByPublicId(String publicId);
}
