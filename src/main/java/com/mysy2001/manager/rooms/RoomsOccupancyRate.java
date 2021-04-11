package com.mysy2001.manager.rooms;

import com.mysy2001.manager.rooms.occupancy.OccupancyDetails;

import lombok.Value;

@Value(staticConstructor = "of")
public class RoomsOccupancyRate {

    OccupancyDetails premiumOccupancyDetails;

    OccupancyDetails economyOccupancyDetails;

}
