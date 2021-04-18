package com.mysy2001.hotels.occupancy.rest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;

import com.mysy2001.hotels.occupancy.domain.OccupancyCalculationRequest;
import com.mysy2001.hotels.occupancy.domain.GuestPaymentBasedOccupancyManager;

class OccupancyCalculationResourceTest {

    @Test
    void should_invoke_OccupancyManager_calculateOccupancy_with_provided_parameter() {

        final GuestPaymentBasedOccupancyManager occupancyManager = mock(GuestPaymentBasedOccupancyManager.class);
        final OccupancyCalculationResource objectUnderTest = new OccupancyCalculationResource(occupancyManager, null);

        final OccupancyCalculationRequest request = new OccupancyCalculationRequest(1, 2);
        objectUnderTest.calculateOccupancy(request);

        verify(occupancyManager).calculateOccupancy(request);
        verifyNoMoreInteractions(occupancyManager);
    }

}