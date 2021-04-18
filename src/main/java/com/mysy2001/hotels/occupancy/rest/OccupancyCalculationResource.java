package com.mysy2001.hotels.occupancy.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysy2001.hotels.occupancy.domain.GuestPaymentsRequest;
import com.mysy2001.hotels.occupancy.domain.GuestPaymentsDataManager;
import com.mysy2001.hotels.occupancy.domain.OccupancyCalculationRequest;
import com.mysy2001.hotels.occupancy.domain.OccupancyCalculationResult;
import com.mysy2001.hotels.occupancy.domain.OccupancyManager;

@RestController
@RequestMapping(path = "/occupancy")
public class OccupancyCalculationResource {

    private final OccupancyManager occupancyManager;

    private final GuestPaymentsDataManager guestsDataManager;

    @Autowired
    public OccupancyCalculationResource(final OccupancyManager roomsOccupancyManager, final GuestPaymentsDataManager guestsDataManager) {
        this.occupancyManager = roomsOccupancyManager;
        this.guestsDataManager = guestsDataManager;
    }

    @PostMapping(path = "/calculation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OccupancyCalculationResult calculateOccupancy(@RequestBody final OccupancyCalculationRequest request) {
        return this.occupancyManager.calculateOccupancy(request);
    }

    @PostMapping(path = "/guests/payments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setGuestPayments(@RequestBody final GuestPaymentsRequest request) {
        guestsDataManager.setGuestsData(request);
    }
}

