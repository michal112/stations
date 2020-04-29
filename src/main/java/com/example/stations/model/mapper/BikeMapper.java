package com.example.stations.model.mapper;

import com.example.stations.model.entity.BikeEntity;
import com.example.stations.model.payload.BikePayload;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class BikeMapper {

    public BikePayload mapToPayload(BikeEntity entity) {
        return BikePayload.builder()
                .publicId(entity.getPublicId())
                .number(entity.getNumber())
                .build();
    }

    public BikeEntity mapToEntity(BikePayload payload) {
        return BikeEntity.builder()
                .publicId(Optional.ofNullable(payload.getPublicId()).orElse(UUID.randomUUID().toString()))
                .number(payload.getNumber())
                .build();
    }
}
