package com.example.stations.data;

import com.example.stations.model.payload.BikePayload;

import java.util.UUID;

public class BikeFactory {

    private static final String NUMBER =  "B/00001";

    public static BikePayload createSimpleBike() {
        return BikePayload.builder()
                .publicId(UUID.randomUUID().toString())
                .number(NUMBER)
                .build();
    }
}
