package com.example.stations.model.mapper;

import com.example.stations.model.entity.BikeEntity;
import com.example.stations.model.entity.RentEntity;
import com.example.stations.model.payload.RentPayload;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RentMapper {

    public RentPayload mapToPayload(RentEntity entity) {
        return RentPayload.builder()
                .publicId(entity.getPublicId())
                .bikePublicId(entity.getBike().getPublicId())
                .build();
    }

    public RentEntity mapToEntity(RentPayload payload, BikeEntity bike) {
        return RentEntity.builder()
                .publicId(Optional.ofNullable(payload.getPublicId()).orElse(UUID.randomUUID().toString()))
                .bike(bike)
                .build();
    }
}
