package com.example.stations.model.repository;

import com.example.stations.model.entity.StandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandRepository extends JpaRepository<StandEntity, Long> {
}
