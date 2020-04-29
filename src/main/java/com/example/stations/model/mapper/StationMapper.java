package com.example.stations.model.mapper;

import com.example.stations.model.entity.StationEntity;
import com.example.stations.model.payload.StationPayload;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class StationMapper {

    private final StandMapper standMapper;

    public StationMapper(StandMapper standMapper) {
        this.standMapper = standMapper;
    }

    public StationPayload mapToPayload(StationEntity entity) {
        return StationPayload.builder()
                .publicId(entity.getPublicId())
                .name(entity.getName())
                .number(entity.getNumber())
                .stands(entity.getStands().stream()
                            .map(standMapper::mapToPayload)
                            .collect(Collectors.toSet()))
                .build();
    }

    public StationEntity mapToEntity(StationPayload payload) {
        var entity = StationEntity.builder()
                .publicId(Optional.ofNullable(payload.getPublicId()).orElse(UUID.randomUUID().toString()))
                .name(payload.getName())
                .number(payload.getNumber())
                .stands(payload.getStands().stream()
                        .map(standMapper::mapToEntity)
                        .collect(Collectors.toSet()))
                .build();
        entity.getStands().forEach(stand -> stand.setStation(entity));

        return entity;
    }
}
