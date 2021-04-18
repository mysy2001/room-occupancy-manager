package com.mysy2001.hotels.occupancy.domain.booking;

import com.mysy2001.hotels.occupancy.domain.RoomAssignments;
import com.mysy2001.hotels.occupancy.domain.rooms.AvailableRooms;

public interface BookingStrategy {

    RoomAssignments bookGuests(final AvailableRooms rooms, final RoomAssignments assignments);
}
