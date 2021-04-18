package com.mysy2001.hotels.occupancy.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

class RoomAssignments {

    private final Map<RoomCategory, List<RoomCategoryAssignment>> assignments;

    public RoomAssignments() {
        assignments = new EnumMap<>(RoomCategory.class);
        Arrays.stream(RoomCategory.values())
                .forEach(category -> assignments.put(category, new ArrayList<>()));
    }

    public void append(final RoomCategoryAssignment assignment) {
        this.assignments.computeIfPresent(assignment.getCategory(), (category, categoryAssignments) -> {
            categoryAssignments.add(assignment);
            return categoryAssignments;
        });
    }

    public void appendAll(final Collection<RoomCategoryAssignment> assignments) {
        assignments.forEach(this::append);
    }

    public List<RoomCategoryAssignment> getPremiumAssignments() {
        return List.copyOf(assignments.getOrDefault(RoomCategory.PREMIUM, Collections.emptyList()));
    }

    public int getPremiumAssignmentsCount() {
        return getPremiumAssignments().size();
    }

    public int getEconomyAssignmentsCount() {
        return getEconomyAssignments().size();
    }

    public List<RoomCategoryAssignment> getEconomyAssignments() {
        return List.copyOf(assignments.getOrDefault(RoomCategory.ECONOMY, Collections.emptyList()));
    }

    public List<RoomCategoryAssignment> getAssignments(final RoomCategory category) {
        return assignments.getOrDefault(category, Collections.emptyList());
    }

    public RoomAssignments selectForBooking(final AvailableRooms rooms, final BookingStrategy strategy) {
        return strategy.bookGuests(rooms, this);
    }
}

interface BookingStrategy {

    RoomAssignments bookGuests(final AvailableRooms rooms, final RoomAssignments assignments);
}

class BookingWithUpgradeStrategy implements BookingStrategy {

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

