package com.mysy2001.hotels.occupancy.domain.rooms;

import com.mysy2001.hotels.occupancy.domain.booking.BookingStrategy;

public interface RoomAssignmentsManager {

    Rooms getRoomAssignments(AvailableRooms rooms, BookingStrategy bookingStrategy);
}
