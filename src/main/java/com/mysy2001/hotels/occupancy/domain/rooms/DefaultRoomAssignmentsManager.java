package com.mysy2001.hotels.occupancy.domain.rooms;

import java.util.List;

import com.mysy2001.hotels.occupancy.domain.booking.BookingOrderStrategy;
import com.mysy2001.hotels.occupancy.domain.booking.BookingWithUpgradeStrategy;
import com.mysy2001.hotels.occupancy.domain.guests.GuestsDataProvider;
import com.mysy2001.hotels.occupancy.domain.rooms.AvailableRooms;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomAssignments;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategory;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategoryAssignment;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategoryProvider;

public class DefaultRoomAssignmentsManager {

    private final GuestsDataProvider<Integer> guestsDataProvider;

    private final BookingOrderStrategy<Integer> bookingOrderStrategy;

    private final RoomCategoryProvider<Integer> roomCategoryProvider;

    public DefaultRoomAssignmentsManager(final GuestsDataProvider<Integer> guestsDataProvider, final BookingOrderStrategy<Integer> bookingOrderStrategy,
            final RoomCategoryProvider<Integer> roomCategoryProvider) {
        this.guestsDataProvider = guestsDataProvider;
        this.bookingOrderStrategy = bookingOrderStrategy;
        this.roomCategoryProvider = roomCategoryProvider;
    }

    public RoomAssignments getGuestsRoomAssignments(final AvailableRooms rooms) {
        final RoomAssignments assignments = getGuestsRoomAssignments();
        return assignments.selectForBooking(rooms, new BookingWithUpgradeStrategy());
    }

    private RoomAssignments getGuestsRoomAssignments() {
        final RoomAssignments assignments = new RoomAssignments();
        appendGuestsAssignments(assignments);
        return assignments;
    }

    private void appendGuestsAssignments(final RoomAssignments assignments) {
        getGuestsData().forEach(integer -> {
            final RoomCategory category = roomCategoryProvider.getRoomCategory(integer);
            assignments.append(new RoomCategoryAssignment(category, integer));
        });
    }

    private List<Integer> getGuestsData() {
        return bookingOrderStrategy.createBookingOrder(guestsDataProvider.getGuestsData());
    }
}
