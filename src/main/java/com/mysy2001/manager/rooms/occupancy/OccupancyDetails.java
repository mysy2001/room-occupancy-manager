package com.mysy2001.manager.rooms.occupancy;

import lombok.Value;

@Value(staticConstructor = "of")
public class OccupancyDetails {

    int usage;
    int price;
}
