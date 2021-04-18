package com.mysy2001.hotels.occupancy.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.mysy2001.hotels.occupancy.domain.booking.BookingStrategy;
import com.mysy2001.hotels.occupancy.domain.rooms.AvailableRooms;

public class RoomAssignments {

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

