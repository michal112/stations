package com.example.demo.service;

import com.example.demo.model.entity.BikeEntity;
import com.example.demo.model.entity.RentEntity;
import com.example.demo.model.entity.StandEntity;
import com.example.demo.model.mapper.StationMapper;
import com.example.demo.model.payload.StationInfoPayload;
import com.example.demo.model.payload.StationPayload;
import com.example.demo.model.repository.RentRepository;
import com.example.demo.model.repository.StationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class StationService {

    private final StationRepository stationRepository;

    private final StationMapper stationMapper;

    private final RentRepository rentRepository;

    public StationService(StationRepository stationRepository, StationMapper stationMapper, RentRepository rentRepository) {
        this.stationRepository = stationRepository;
        this.stationMapper = stationMapper;
        this.rentRepository = rentRepository;
    }

    public StationPayload createStation(StationPayload stationPayload) {
        var entity = stationMapper.mapToEntity(stationPayload);
        return stationMapper.mapToPayload(stationRepository.save(entity));
    }

    public void updateStation(String publicId, StationPayload stationPayload) {
        var updated = stationMapper.mapToEntity(stationPayload);
        var original = stationRepository.getByPublicId(publicId);

        updated.setId(original.getId());
        stationRepository.save(updated);
    }

    public void deleteStation(String publicId) {
        var entity = stationRepository.getByPublicId(publicId);
        stationRepository.delete(entity);
    }

    public List<StationInfoPayload> getStations() {
        var stations = stationRepository.findAll();
        var unavailableBikes = StreamSupport.stream(rentRepository.findAll().spliterator(), false)
                .map(RentEntity::getBikeEntity)
                .map(BikeEntity::getPublicId)
                .collect(Collectors.toList());

        return StreamSupport.stream(stations.spliterator(), false)
                .map(station -> StationInfoPayload.builder()
                    .name(station.getName())
                    .standsAvailable(station.getStands().stream()
                            .filter(StandEntity::getAvailable)
                            .count())
                    .standsUnavailable(station.getStands().stream()
                            .filter(stand -> !stand.getAvailable())
                            .count())
                    .bikesAvailable(station.getBikes().size() - station.getBikes().stream()
                            .map(BikeEntity::getPublicId)
                            .filter(unavailableBikes::contains)
                            .count())
                    .build()
                )
                .collect(Collectors.toList());
    }
}
