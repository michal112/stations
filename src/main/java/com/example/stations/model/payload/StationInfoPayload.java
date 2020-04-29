package com.example.stations.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationInfoPayload {

    private String name;

    private String number;

    private Long standsAvailable;

    private Long standsUnavailable;

    private Long bikesAvailable;
}
