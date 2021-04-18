package com.mysy2001.hotels.occupancy.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

class RoomCategoryAssignments {

    private final Map<RoomCategory, List<RoomCategoryAssignment>> assignments;

    public RoomCategoryAssignments() {
        assignments = new EnumMap<>(RoomCategory.class);
        Arrays.stream(RoomCategory.values())
                .forEach(category -> assignments.put(category, new ArrayList<>()));

    }

    public void append(RoomCategoryAssignment assignment) {
        this.assignments.computeIfPresent(assignment.getCategory(), (category, categoryAssignments) -> {
            categoryAssignments.add(assignment);
            return categoryAssignments;
        });
    }

    public List<RoomCategoryAssignment> getPremiumAssignments() {
        return assignments.getOrDefault(RoomCategory.PREMIUM, Collections.emptyList());
    }

    public List<RoomCategoryAssignment> getEconomyAssignments() {
        return assignments.getOrDefault(RoomCategory.ECONOMY, Collections.emptyList());
    }

    public List<RoomCategoryAssignment> getAssignments(final RoomCategory category) {
        return assignments.getOrDefault(category, Collections.emptyList());
    }

    public RoomCategoryAssignments selectForBooking(final AvailableRooms availableRooms) {

        final List<RoomCategoryAssignment> premiumCandidates = assignments.get(RoomCategory.PREMIUM);
        final List<RoomCategoryAssignment> economyCandidates = assignments.get(RoomCategory.ECONOMY);

        int premiumCandidatesCount = getPremiumAssignments().size();
        int economyCandidatesCount = getEconomyAssignments().size();
        final int availablePremiumRooms = availableRooms.getAvailableRooms(RoomCategory.PREMIUM);
        final int availableEconomyRooms = availableRooms.getAvailableRooms(RoomCategory.ECONOMY);

        final RoomCategoryAssignments result = new RoomCategoryAssignments();
        if ( premiumCandidatesCount < availablePremiumRooms && economyCandidatesCount > availableEconomyRooms ) {
            int availableUpgrades = availablePremiumRooms - premiumCandidatesCount;
            final List<RoomCategoryAssignment> premiumAssignments = premiumCandidates.subList(0, premiumCandidatesCount);
            economyCandidates.subList(0, availableUpgrades)
                    .forEach(assignment -> premiumAssignments.add(new RoomCategoryAssignment(RoomCategory.PREMIUM, assignment.getGuestData())));
            result.assignments.put(RoomCategory.PREMIUM, premiumAssignments);
            result.assignments.put(RoomCategory.ECONOMY, economyCandidates.subList(availableUpgrades, availableUpgrades + availableEconomyRooms));
        } else {
            result.assignments.put(RoomCategory.PREMIUM, premiumCandidates.subList(0, Math.min(premiumCandidatesCount, availablePremiumRooms)));
            result.assignments.put(RoomCategory.ECONOMY, economyCandidates.subList(0, Math.min(economyCandidatesCount, availableEconomyRooms)));
        }
        return result;
    }

}
