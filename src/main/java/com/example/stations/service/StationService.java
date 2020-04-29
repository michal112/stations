package com.example.stations.service;

import com.example.stations.model.entity.BikeEntity;
import com.example.stations.model.entity.RentEntity;
import com.example.stations.model.mapper.StationInfoMapper;
import com.example.stations.model.mapper.StationMapper;
import com.example.stations.model.payload.StationInfoPayload;
import com.example.stations.model.payload.StationPayload;
import com.example.stations.model.repository.RentRepository;
import com.example.stations.model.repository.StationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class StationService {

    private final StationRepository stationRepository;

    private final StationMapper stationMapper;

    private final RentRepository rentRepository;

    private final StationInfoMapper stationInfoMapper;

    public StationService(StationRepository stationRepository, StationMapper stationMapper, RentRepository rentRepository, StationInfoMapper stationInfoMapper) {
        this.stationRepository = stationRepository;
        this.stationMapper = stationMapper;
        this.rentRepository = rentRepository;
        this.stationInfoMapper = stationInfoMapper;
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

    public List<StationInfoPayload> getStationsInfo() {
        var borrowedBikes = StreamSupport.stream(rentRepository.findAll().spliterator(), false)
                .map(RentEntity::getBike)
                .map(BikeEntity::getPublicId)
                .collect(Collectors.toList());

        return StreamSupport.stream(stationRepository.findAll().spliterator(), false)
                .map(station -> stationInfoMapper.mapToPayload(station, borrowedBikes))
                .collect(Collectors.toList());
    }

    public List<StationPayload> getStations() {
        return stationRepository.findAll().stream()
                .map(stationMapper::mapToPayload)
                .collect(Collectors.toList());
    }
}
