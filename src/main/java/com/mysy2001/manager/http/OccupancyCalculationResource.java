package com.mysy2001.manager.http;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysy2001.manager.rooms.RoomsOccupancyManager;
import com.mysy2001.manager.rooms.occupancy.OccupancyCalculationResult;
import com.mysy2001.manager.rooms.occupancy.OccupancyCalculationRequest;

@RestController
public class OccupancyCalculationResource {

    private final RoomsOccupancyManager occupancyManager;

    public OccupancyCalculationResource() {
        this.occupancyManager = new RoomsOccupancyManager();
    }

    @PostMapping(path = "/occupancy/calculation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OccupancyCalculationResult calculateOccupancy(@RequestBody OccupancyCalculationRequest request) {
        return this.occupancyManager.calculateOccupancy(request);
    }
}

