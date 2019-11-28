package com.example.demo.model.repository;

import com.example.demo.model.entity.StationEntity;
import org.springframework.data.repository.CrudRepository;

public interface StationRepository extends CrudRepository<StationEntity, Long> {

    StationEntity getByPublicId(String publicId);
}
