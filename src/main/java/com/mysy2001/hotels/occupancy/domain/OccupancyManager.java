package com.mysy2001.hotels.occupancy.domain;

import com.mysy2001.hotels.occupancy.domain.rooms.AvailableRooms;

public interface OccupancyManager {

    OccupancyCalculationResult calculateOccupancy(AvailableRooms rooms);
}
