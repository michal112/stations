package com.example.stations.data;

import com.example.stations.model.payload.RentPayload;

public class RentFactory {

    public static RentPayload createSimpleRent(String bikePublicId) {
        return RentPayload.builder()
                .bikePublicId(bikePublicId)
                .build();
    }
}
