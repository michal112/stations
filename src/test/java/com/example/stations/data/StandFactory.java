package com.example.stations.data;

import com.example.stations.model.payload.BikePayload;
import com.example.stations.model.payload.StandPayload;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

public class StandFactory {

    private static final String NUMBER = "SN/00001";

    public static StandPayload createSimpleStand(boolean available) {
        Set<BikePayload> bikes = new HashSet<>();

        IntStream.range(0, 5).forEach(i -> bikes.add(BikeFactory.createSimpleBike()));

        return StandPayload.builder()
                .publicId(UUID.randomUUID().toString())
                .number(NUMBER)
                .available(available)
                .bikes(bikes)
                .build();
    }
}
