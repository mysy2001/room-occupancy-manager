package com.mysy2001.hotels.occupancy.domain.room;

import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategory;

public interface Room {

    RoomCategory getCategory();

    Price getPrice();
}

