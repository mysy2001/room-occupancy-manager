package com.mysy2001.hotels.occupancy.domain.rooms;

import java.util.List;

import com.mysy2001.hotels.occupancy.domain.booking.BookingOrderStrategy;
import com.mysy2001.hotels.occupancy.domain.booking.BookingWithUpgradeStrategy;
import com.mysy2001.hotels.occupancy.domain.guests.GuestsDataProvider;

public class DefaultRoomAssignmentsManager implements RoomAssignmentsManager {

    private final GuestsDataProvider<Integer> guestsDataProvider;

    private final BookingOrderStrategy<Integer> bookingOrderStrategy;

    private final RoomCategoryProvider<Integer> roomCategoryProvider;

    public DefaultRoomAssignmentsManager(final GuestsDataProvider<Integer> guestsDataProvider, final BookingOrderStrategy<Integer> bookingOrderStrategy,
            final RoomCategoryProvider<Integer> roomCategoryProvider) {
        this.guestsDataProvider = guestsDataProvider;
        this.bookingOrderStrategy = bookingOrderStrategy;
        this.roomCategoryProvider = roomCategoryProvider;
    }

    @Override
    public RoomAssignments getRoomAssignments(final AvailableRooms rooms) {
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