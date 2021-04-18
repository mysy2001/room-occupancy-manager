package com.mysy2001.hotels.occupancy.domain.booking;

import com.mysy2001.hotels.occupancy.domain.rooms.RoomAssignments;
import com.mysy2001.hotels.occupancy.domain.rooms.AvailableRooms;

public interface BookingStrategy {

    BookingStrategy bookingWithUpgrades = new BookingWithUpgradeStrategy();

    RoomAssignments bookGuests(final AvailableRooms rooms, final RoomAssignments assignments);
}
