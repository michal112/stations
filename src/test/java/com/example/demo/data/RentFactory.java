package com.example.demo.data;

import com.example.demo.model.payload.RentPayload;

import java.util.UUID;

public class RentFactory {

    public static RentPayload createSimpleRent(String bikePublicId) {
        return RentPayload.builder()
                .publicId(UUID.randomUUID().toString())
                .bikePublicId(bikePublicId)
                .build();
    }
}
