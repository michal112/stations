package com.example.demo.model.mapper;

import com.example.demo.model.entity.StationEntity;
import com.example.demo.model.payload.StationPayload;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StationMapper implements Mapper<StationEntity, StationPayload> {

    private final BikeMapper bikeMapper;

    private final StandMapper standMapper;

    public StationMapper(BikeMapper bikeMapper, StandMapper standMapper) {
        this.bikeMapper = bikeMapper;
        this.standMapper = standMapper;
    }

    @Override
    public StationPayload mapToPayload(StationEntity entity) {
        return StationPayload.builder()
                .publicId(entity.getPublicId())
                .name(entity.getName())
                .bikes(entity.getBikes().stream()
                            .map(bikeMapper::mapToPayload)
                            .collect(Collectors.toSet()))
                .stands(entity.getStands().stream()
                            .map(standMapper::mapToPayload)
                            .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public StationEntity mapToEntity(StationPayload payload) {
        var entity = StationEntity.builder()
                .publicId(payload.getPublicId())
                .name(payload.getName())
                .bikes(payload.getBikes().stream()
                        .map(bikeMapper::mapToEntity)
                        .collect(Collectors.toSet()))
                .stands(payload.getStands().stream()
                        .map(standMapper::mapToEntity)
                        .collect(Collectors.toSet()))
                .build();
        entity.getBikes().forEach(bike -> bike.setStation(entity));
        entity.getStands().forEach(stand -> stand.setStation(entity));

        return entity;
    }
}
