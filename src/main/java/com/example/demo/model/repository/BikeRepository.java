package com.example.demo.model.repository;

import com.example.demo.model.entity.BikeEntity;
import org.springframework.data.repository.CrudRepository;

public interface BikeRepository extends CrudRepository<BikeEntity, Long> {

    BikeEntity getByPublicId(String publicId);
}
