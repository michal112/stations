package com.example.stations.model.repository;

import com.example.stations.model.entity.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentRepository extends JpaRepository<RentEntity, Long> {

    RentEntity getByPublicId(String publicId);
}
