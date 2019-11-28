package com.example.demo.model.mapper;

import com.example.demo.model.entity.RentEntity;
import com.example.demo.model.payload.RentPayload;
import com.example.demo.model.repository.BikeRepository;
import org.springframework.stereotype.Component;

@Component
public class RentMapper implements Mapper<RentEntity, RentPayload> {

    private final BikeRepository bikeRepository;

    public RentMapper(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    @Override
    public RentPayload mapToPayload(RentEntity entity) {
        return RentPayload.builder()
                .publicId(entity.getPublicId())
                .bikePublicId(entity.getBikeEntity().getPublicId())
                .build();
    }

    @Override
    public RentEntity mapToEntity(RentPayload payload) {
        return RentEntity.builder()
                .publicId(payload.getPublicId())
                .bikeEntity(bikeRepository.getByPublicId(payload.getBikePublicId()))
                .build();
    }
}
