package com.example.demo.model.mapper;

import com.example.demo.model.entity.StandEntity;
import com.example.demo.model.payload.StandPayload;
import org.springframework.stereotype.Component;

@Component
public class StandMapper implements Mapper<StandEntity, StandPayload> {

    @Override
    public StandPayload mapToPayload(StandEntity entity) {
        return StandPayload.builder()
                .publicId(entity.getPublicId())
                .available(entity.getAvailable())
                .build();
    }

    @Override
    public StandEntity mapToEntity(StandPayload payload) {
        return StandEntity.builder()
                .publicId(payload.getPublicId())
                .available(payload.getAvailable())
                .build();
    }
}
