package com.mysy2001.hotels.occupancy.domain.booking;

import java.util.List;

import com.mysy2001.hotels.occupancy.domain.rooms.AvailableRooms;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomAssignments;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategory;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategoryAssignment;

public class BookingWithUpgradeStrategy implements BookingStrategy {

    public RoomAssignments bookGuests(final AvailableRooms rooms, final RoomAssignments assignments) {
        final RoomAssignments result = new RoomAssignments();
        final int upgrades = calculateUpgrades(rooms, assignments);
        appendPremiumRoomForBooking(rooms.getPremiumRooms(), assignments.getPremiumAssignments(), result);
        appendEconomyAssignmentsForBooking(rooms.getEconomyRooms(), upgrades, assignments.getEconomyAssignments(), result);
        return result;
    }

    private void appendPremiumRoomForBooking(final int availableRoomsCount, final List<RoomCategoryAssignment> assignments,
            final RoomAssignments assignmentsToBook) {
        final int endIndex = Math.min(availableRoomsCount, assignments.size());
        final List<RoomCategoryAssignment> assignmentsChunk = assignments.subList(0, endIndex);
        assignmentsToBook.appendAll(assignmentsChunk);
    }

    private void appendEconomyAssignmentsForBooking(final int availableRoomsCount, final int upgrades, final List<RoomCategoryAssignment> assignments,
            final RoomAssignments assignmentsToBook) {
        if ( upgrades > 0 ) {
            assignments.subList(0, upgrades)
                    .forEach(assignment -> assignmentsToBook.append(new RoomCategoryAssignment(RoomCategory.PREMIUM, assignment.getGuestData())));
            assignmentsToBook.appendAll(assignments.subList(upgrades, upgrades + availableRoomsCount));
        } else {
            final int endIndex = Math.min(availableRoomsCount, assignments.size());
            assignmentsToBook.appendAll(assignments.subList(upgrades, endIndex));
        }
    }

    private int calculateUpgrades(final AvailableRooms rooms, final RoomAssignments assignments) {
        int upgrades = 0;
        final int premiumGuestsToBook = assignments.getPremiumAssignmentsCount();
        final int availablePremiumRoomsAfterBooking = rooms.getPremiumRooms() - premiumGuestsToBook;
        if ( availablePremiumRoomsAfterBooking > 0 ) {
            final int economyGuestsToBook = assignments.getEconomyAssignmentsCount();
            final int economyGuestsWithoutRoomAfterBooking = economyGuestsToBook - rooms.getEconomyRooms();
            if ( economyGuestsWithoutRoomAfterBooking > 0 ) {
                upgrades = Math.min(availablePremiumRoomsAfterBooking, economyGuestsWithoutRoomAfterBooking);
            }
        }
        return upgrades;
    }
}
