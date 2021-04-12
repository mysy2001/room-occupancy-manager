package com.mysy2001.hotels.occupancy.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class OccupancyCalculationResult {

    OccupancyDetails premiumOccupancyDetails;

    OccupancyDetails economyOccupancyDetails;

}
