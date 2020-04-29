package com.example.stations.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandPayload {

    private String publicId;

    private Boolean available;

    private String number;

    private Set<BikePayload> bikes;
}
