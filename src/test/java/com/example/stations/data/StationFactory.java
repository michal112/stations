package com.example.stations.data;

import com.example.stations.model.payload.StandPayload;
import com.example.stations.model.payload.StationPayload;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

public class StationFactory {

    private static final String NAME = "name";

    private static final String NUMBER = "ST/00001";

    public static StationPayload createSimpleStation() {
        Set<StandPayload> stands = new HashSet<>();

        IntStream.range(0, 5).forEach(i -> stands.add(StandFactory.createSimpleStand(i % 2 == 0)));

        return StationPayload.builder()
                .publicId(UUID.randomUUID().toString())
                .number(NUMBER)
                .name(NAME)
                .stands(stands)
                .build();
    }
}
