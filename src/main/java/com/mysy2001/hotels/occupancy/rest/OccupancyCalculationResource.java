package com.mysy2001.hotels.occupancy.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysy2001.hotels.occupancy.domain.OccupancyCalculationRequest;
import com.mysy2001.hotels.occupancy.domain.OccupancyCalculationResult;
import com.mysy2001.hotels.occupancy.domain.OccupancyManager;
import com.mysy2001.hotels.occupancy.domain.guests.GuestPaymentsDataManager;
import com.mysy2001.hotels.occupancy.domain.guests.GuestPaymentsRequest;
import com.mysy2001.hotels.occupancy.domain.rooms.AvailableRooms;

@RestController
@RequestMapping(path = "/occupancy")
public class OccupancyCalculationResource {

    private final OccupancyManager occupancyManager;

    private final GuestPaymentsDataManager guestsDataManager;

    @Autowired
    public OccupancyCalculationResource(final OccupancyManager occupancyManager, final GuestPaymentsDataManager guestsDataManager) {
        this.occupancyManager = occupancyManager;
        this.guestsDataManager = guestsDataManager;
    }

    @PostMapping(path = "/calculation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OccupancyCalculationResult calculateOccupancy(@RequestBody final OccupancyCalculationRequest request) {
        final AvailableRooms rooms = createAvailableRooms(request);
        return this.occupancyManager.calculateOccupancy(rooms);
    }

    private AvailableRooms createAvailableRooms(OccupancyCalculationRequest request) {
        return new AvailableRooms().withEconomyRooms(request.getFreeEconomyRooms())
                .withPremiumRooms(request.getFreePremiumRooms());
    }

    @PostMapping(path = "/guests/payments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setGuestPayments(@RequestBody final GuestPaymentsRequest request) {
        guestsDataManager.setGuestsData(request);
    }
}

