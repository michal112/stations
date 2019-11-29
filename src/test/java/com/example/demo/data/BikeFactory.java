package com.example.demo.data;

import com.example.demo.model.payload.BikePayload;

import java.util.UUID;

public class BikeFactory {

    public static BikePayload createSimpleBike() {
        return BikePayload.builder()
                .publicId(UUID.randomUUID().toString())
                .build();
    }
}
