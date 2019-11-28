package com.example.demo.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationPayload {

    private String publicId;

    private String name;

    private Set<BikePayload> bikes;

    private Set<StandPayload> stands;
}
