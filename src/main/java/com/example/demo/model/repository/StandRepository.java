package com.example.demo.model.repository;

import com.example.demo.model.entity.StandEntity;
import org.springframework.data.repository.CrudRepository;

public interface StandRepository extends CrudRepository<StandEntity, Long> {

    StandEntity getByPublicId(String publicId);
}
