package com.example.demo.data;

import com.example.demo.model.payload.BikePayload;
import com.example.demo.model.payload.StandPayload;
import com.example.demo.model.payload.StationPayload;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

public class StationFactory {

    private static String NAME = "name";

    public static StationPayload createSimpleStation() {
        Set<StandPayload> stands = new HashSet<>();
        Set<BikePayload> bikes = new HashSet<>();

        IntStream.range(0, 5).forEach(i -> {
            stands.add(StandFactory.createSimpleStand(i % 2 == 0));
            bikes.add(BikeFactory.createSimpleBike());
        });

        return StationPayload.builder()
                .publicId(UUID.randomUUID().toString())
                .name(NAME)
                .stands(stands)
                .bikes(bikes)
                .build();
    }
}
