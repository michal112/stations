package com.example.stations.model.mapper;

import com.example.stations.model.entity.StandEntity;
import com.example.stations.model.payload.StandPayload;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class StandMapper {

    private final BikeMapper bikeMapper;

    public StandMapper(BikeMapper bikeMapper) {
        this.bikeMapper = bikeMapper;
    }

    public StandPayload mapToPayload(StandEntity entity) {
        return StandPayload.builder()
                .publicId(entity.getPublicId())
                .available(entity.getAvailable())
                .number(entity.getNumber())
                .bikes(entity.getBikes().stream()
                            .map(bikeMapper::mapToPayload)
                            .collect(Collectors.toSet()))
                .build();
    }

    public StandEntity mapToEntity(StandPayload payload) {
        var entity = StandEntity.builder()
                .publicId(Optional.ofNullable(payload.getPublicId()).orElse(UUID.randomUUID().toString()))
                .available(payload.getAvailable())
                .number(payload.getNumber())
                .bikes(payload.getBikes().stream()
                            .map(bikeMapper::mapToEntity)
                            .collect(Collectors.toSet()))
                .build();
        entity.getBikes().forEach(bike -> bike.setStand(entity));

        return entity;
    }
}
