package com.mysy2001.hotels.occupancy.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class OccupancyDetails {

    int usage;
    int price;
}
