package com.mysy2001.hotels.occupancy.domain;

import java.util.Collection;

import lombok.Value;

@Value(staticConstructor = "of")
public class OccupancyCalculationResult {

    Collection<OccupancyDetails> occupancy;

}
