package com.example.demo.model.mapper;

import com.example.demo.model.entity.BikeEntity;
import com.example.demo.model.payload.BikePayload;
import org.springframework.stereotype.Component;

@Component
public class BikeMapper implements Mapper<BikeEntity, BikePayload> {

    @Override
    public BikePayload mapToPayload(BikeEntity entity) {
        return BikePayload.builder()
                .publicId(entity.getPublicId())
                .build();
    }

    @Override
    public BikeEntity mapToEntity(BikePayload payload) {
        return BikeEntity.builder()
                .publicId(payload.getPublicId())
                .build();
    }
}
