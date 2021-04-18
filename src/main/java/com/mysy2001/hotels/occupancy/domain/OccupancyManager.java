package com.mysy2001.hotels.occupancy.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Value;

public class OccupancyManager {

    private final BookingOrderStrategy<Integer> bookingOrderStrategy = new FromHighestPaymentBookingOrderStrategy();

    private final GuestsDataProvider guestsDataProvider;

    private final RoomCategoryProvider<Integer> roomCategoryProvider = new PaymentBasedRoomCategoryProvider();

    public OccupancyManager(final GuestsDataProvider guestsDataProvider) {
        this.guestsDataProvider = guestsDataProvider;
    }

    public OccupancyCalculationResult calculateOccupancy(final OccupancyCalculationRequest request) {
        final AvailableRooms availableRooms = createAvailableRooms(request);
        return this.calculateOccupancy(availableRooms);
    }

    private AvailableRooms createAvailableRooms(final OccupancyCalculationRequest request) {
        return new AvailableRooms().withPremiumRooms(request.getFreePremiumRooms())
                .withEconomyRooms(request.getFreeEconomyRooms());
    }

    OccupancyCalculationResult calculateOccupancy(final AvailableRooms availableRooms) {
        final RoomCategoryAssignments assignments = getGuestsRoomAssignments();
        final RoomCategoryAssignments bookingAssignments = assignments.selectForBooking(availableRooms);
        return createResult(bookingAssignments);

    }

    private OccupancyCalculationResult createResult(final RoomCategoryAssignments assignments) {
        final List<OccupancyDetails> occupancyDetails = Arrays.stream(RoomCategory.values())
                .map(category -> {
                    final List<RoomCategoryAssignment> categoryAssignments = assignments.getAssignments(category);
                    return createOccupancyDetails(category, categoryAssignments);
                })
                .collect(Collectors.toList());
        return OccupancyCalculationResult.of(occupancyDetails);
    }

    private List<Integer> getGuestsData() {
        return bookingOrderStrategy.createBookingOrder(guestsDataProvider.getGuestsData());
    }

    private RoomCategoryAssignments getGuestsRoomAssignments() {

        final RoomCategoryAssignments assignments = new RoomCategoryAssignments();
        getGuestsData().forEach(integer -> {
            final RoomCategory category = roomCategoryProvider.getRoomCategory(integer);
            assignments.append(new RoomCategoryAssignment(category, integer));
        });
        return assignments;
    }

    private OccupancyDetails createOccupancyDetails(final RoomCategory category, final List<RoomCategoryAssignment> assignments) {
        return OccupancyDetails.of(category, assignments.size(), assignments.stream()
                .mapToInt(RoomCategoryAssignment::getGuestData)
                .reduce(0, Integer::sum));
    }
}

@Value
class RoomCategoryAssignment {
    RoomCategory category;

    int guestData;
}

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