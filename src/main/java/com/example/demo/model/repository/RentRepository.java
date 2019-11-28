package com.example.demo.model.repository;

import com.example.demo.model.entity.RentEntity;
import org.springframework.data.repository.CrudRepository;

public interface RentRepository extends CrudRepository<RentEntity, Long> {

    RentEntity getByPublicId(String publicId);
}
