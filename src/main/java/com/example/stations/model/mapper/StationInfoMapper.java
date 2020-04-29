package com.example.stations.model.mapper;

import com.example.stations.model.entity.BikeEntity;
import com.example.stations.model.entity.StandEntity;
import com.example.stations.model.entity.StationEntity;
import com.example.stations.model.payload.StationInfoPayload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StationInfoMapper {

    public StationInfoPayload mapToPayload(StationEntity station, List<String> borrowedBikes) {
        return StationInfoPayload.builder()
                .name(station.getName())
                .number(station.getNumber())
                .standsAvailable(station.getStands().stream()
                        .filter(StandEntity::getAvailable)
                        .count())
                .standsUnavailable(station.getStands().stream()
                        .filter(stand -> !stand.getAvailable())
                        .count())
                .bikesAvailable(station.getStands().stream()
                        .flatMap(stand -> stand.getBikes().stream())
                        .map(BikeEntity::getPublicId)
                        .filter(bike -> !borrowedBikes.contains(bike))
                        .count())
                .build();
    }
}
