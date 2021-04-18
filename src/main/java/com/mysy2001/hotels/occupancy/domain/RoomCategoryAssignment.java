package com.mysy2001.hotels.occupancy.domain;

import lombok.Value;

@Value
class RoomCategoryAssignment {
    RoomCategory category;

    int guestData;
}
