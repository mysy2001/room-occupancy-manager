package com.mysy2001.hotels.occupancy.domain;

public interface OccupancyManager {

    OccupancyCalculationResult calculateOccupancy(OccupancyCalculationRequest request);
}
