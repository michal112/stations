package com.example.demo.service;

import com.example.demo.model.mapper.RentMapper;
import com.example.demo.model.payload.RentPayload;
import com.example.demo.model.repository.RentRepository;
import org.springframework.stereotype.Service;

@Service
public class RentService {

    private final RentRepository rentRepository;

    private final RentMapper rentMapper;

    public RentService(RentRepository rentRepository, RentMapper rentMapper) {
        this.rentRepository = rentRepository;
        this.rentMapper = rentMapper;
    }

    public RentPayload borrowBike(RentPayload rentPayload) {
        var entity = rentMapper.mapToEntity(rentPayload);
        return rentMapper.mapToPayload(rentRepository.save(entity));
    }

    public void returnBike(String publicId) {
        rentRepository.delete(rentRepository.getByPublicId(publicId));
    }
}
