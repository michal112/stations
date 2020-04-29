package com.example.stations.service;

import com.example.stations.model.mapper.RentMapper;
import com.example.stations.model.payload.RentPayload;
import com.example.stations.model.repository.BikeRepository;
import com.example.stations.model.repository.RentRepository;
import org.springframework.stereotype.Service;

@Service
public class RentService {

    private final RentRepository rentRepository;

    private final BikeRepository bikeRepository;

    private final RentMapper rentMapper;

    public RentService(RentRepository rentRepository, BikeRepository bikeRepository, RentMapper rentMapper) {
        this.rentRepository = rentRepository;
        this.bikeRepository = bikeRepository;
        this.rentMapper = rentMapper;
    }

    public RentPayload borrowBike(RentPayload rentPayload) {
        var entity = rentMapper.mapToEntity(rentPayload, bikeRepository.getByPublicId(rentPayload.getBikePublicId()));
        return rentMapper.mapToPayload(rentRepository.save(entity));
    }

    public void returnBike(String publicId) {
        rentRepository.delete(rentRepository.getByPublicId(publicId));
    }
}
