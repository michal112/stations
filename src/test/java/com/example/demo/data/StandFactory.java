package com.example.demo.data;

import com.example.demo.model.payload.StandPayload;

import java.util.UUID;

public class StandFactory {

    public static StandPayload createSimpleStand(boolean available) {
        return StandPayload.builder()
                .publicId(UUID.randomUUID().toString())
                .available(available)
                .build();
    }
}
