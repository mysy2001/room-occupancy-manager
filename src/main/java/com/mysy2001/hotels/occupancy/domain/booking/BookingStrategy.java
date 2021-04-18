package com.mysy2001.hotels.occupancy.domain.booking;

import com.mysy2001.hotels.occupancy.domain.rooms.Rooms;
import com.mysy2001.hotels.occupancy.domain.rooms.AvailableRooms;

public interface BookingStrategy {

    BookingStrategy bookingWithUpgrades = new BookingWithUpgradeStrategy();

    Rooms bookGuests(final AvailableRooms rooms, final Rooms assignments);
}
