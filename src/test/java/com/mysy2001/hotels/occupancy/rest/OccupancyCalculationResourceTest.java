package com.mysy2001.hotels.occupancy.rest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;

import com.mysy2001.hotels.occupancy.domain.DefaultOccupancyManager;
import com.mysy2001.hotels.occupancy.domain.OccupancyCalculationRequest;
import com.mysy2001.hotels.occupancy.domain.rooms.AvailableRooms;

class OccupancyCalculationResourceTest {

    @Test
    void should_invoke_OccupancyManager_calculateOccupancy_with_provided_parameter() {

        final DefaultOccupancyManager occupancyManager = mock(DefaultOccupancyManager.class);
        final OccupancyCalculationResource objectUnderTest = new OccupancyCalculationResource(occupancyManager, null);

        final OccupancyCalculationRequest request = new OccupancyCalculationRequest(1, 2);
        objectUnderTest.calculateOccupancy(request);

        final AvailableRooms rooms = new AvailableRooms().withPremiumRooms(request.getFreePremiumRooms())
                .withEconomyRooms(request.getFreeEconomyRooms());
        verify(occupancyManager).calculateOccupancy(rooms);
        verifyNoMoreInteractions(occupancyManager);
    }

}