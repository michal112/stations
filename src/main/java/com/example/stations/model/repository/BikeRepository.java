package com.example.stations.model.repository;

import com.example.stations.model.entity.BikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeRepository extends JpaRepository<BikeEntity, Long> {

    BikeEntity getByPublicId(String publicId);
}
