package com.mysy2001.hotels.occupancy.domain.rooms;

import lombok.Value;

@Value
public class RoomCategoryAssignment {
    RoomCategory category;

    int guestData;
}
