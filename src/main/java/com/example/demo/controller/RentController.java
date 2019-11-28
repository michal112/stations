package com.example.demo.controller;

import com.example.demo.model.payload.RentPayload;
import com.example.demo.service.RentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RentController {

    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping("/rent/borrow")
    public RentPayload borrowBike(@RequestBody RentPayload rentPayload) {
        return rentService.borrowBike(rentPayload);
    }

    @DeleteMapping("/rent/return/{publicId}")
    public ResponseEntity returnBike(@PathVariable String publicId) {
        rentService.returnBike(publicId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
