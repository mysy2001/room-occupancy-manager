package com.mysy2001.hotels.occupancy.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysy2001.hotels.occupancy.domain.OccupancyCalculationRequest;
import com.mysy2001.hotels.occupancy.domain.OccupancyCalculationResult;
import com.mysy2001.hotels.occupancy.domain.RoomsOccupancyManager;

@RestController
public class OccupancyCalculationResource {

    private final RoomsOccupancyManager occupancyManager;

    @Autowired
    public OccupancyCalculationResource(final RoomsOccupancyManager roomsOccupancyManager) {
        this.occupancyManager = roomsOccupancyManager;
    }

    @PostMapping(path = "/occupancy/calculation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OccupancyCalculationResult calculateOccupancy(@RequestBody OccupancyCalculationRequest request) {
        return this.occupancyManager.calculateOccupancy(request);
    }
}

