package com.example.demo.service;

import com.example.demo.model.entity.StationEntity;
import com.example.demo.model.mapper.StationMapper;
import com.example.demo.model.payload.StationPayload;
import com.example.demo.model.repository.BikeRepository;
import com.example.demo.model.repository.StandRepository;
import com.example.demo.model.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StationService {

    private final StationRepository stationRepository;

    private final StationMapper stationMapper;

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private StandRepository standRepository;

    public StationService(StationRepository stationRepository, StationMapper stationMapper) {
        this.stationRepository = stationRepository;
        this.stationMapper = stationMapper;
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
}
