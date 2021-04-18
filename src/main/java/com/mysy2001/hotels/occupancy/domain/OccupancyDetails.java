package com.mysy2001.hotels.occupancy.domain;

import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategory;

import lombok.Value;

@Value(staticConstructor = "of")
public class OccupancyDetails {

    RoomCategory category;
    int usage;
    int price;
}
