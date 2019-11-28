package com.example.demo.controller;

import com.example.demo.model.payload.StationPayload;
import com.example.demo.service.StationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping("/station")
    public StationPayload createStation(@RequestBody StationPayload stationPayload) {
        return stationService.createStation(stationPayload);
    }

    @PutMapping("/station/{publicId}")
    public ResponseEntity updateStation(@PathVariable String publicId, @RequestBody StationPayload stationPayload) {
        stationService.updateStation(publicId, stationPayload);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/station/{publicId}")
    public ResponseEntity deleteStation(@PathVariable String publicId) {
        stationService.deleteStation(publicId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
