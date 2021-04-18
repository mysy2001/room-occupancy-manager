package com.mysy2001.hotels.occupancy.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mysy2001.hotels.occupancy.domain.guests.GuestsDataProvider;

public class GuestPaymentBasedOccupancyManager implements OccupancyManager {

    private final GuestsRoomAssignmentsManager assignmentsManager;

    public GuestPaymentBasedOccupancyManager(final GuestsDataProvider<Integer> guestsDataProvider) {
        this.assignmentsManager = new GuestsRoomAssignmentsManager(guestsDataProvider, new FromHighestPaymentBookingOrderStrategy(),
                new PaymentBasedRoomCategoryProvider());
    }

    @Override
    public OccupancyCalculationResult calculateOccupancy(final OccupancyCalculationRequest request) {
        final AvailableRooms availableRooms = createAvailableRooms(request);
        return this.calculateOccupancy(availableRooms);
    }

    private AvailableRooms createAvailableRooms(final OccupancyCalculationRequest request) {
        return new AvailableRooms().withPremiumRooms(request.getFreePremiumRooms())
                .withEconomyRooms(request.getFreeEconomyRooms());
    }

    OccupancyCalculationResult calculateOccupancy(final AvailableRooms rooms) {
        final RoomAssignments assignments = this.assignmentsManager.getGuestsRoomAssignments(rooms);
        return createResult(assignments);
    }

    private OccupancyCalculationResult createResult(final RoomAssignments assignments) {
        final List<OccupancyDetails> occupancyDetails = Arrays.stream(RoomCategory.values())
                .map(category -> {
                    final List<RoomCategoryAssignment> categoryAssignments = assignments.getAssignments(category);
                    return createOccupancyDetails(category, categoryAssignments);
                })
                .collect(Collectors.toList());
        return OccupancyCalculationResult.of(occupancyDetails);
    }

    private OccupancyDetails createOccupancyDetails(final RoomCategory category, final List<RoomCategoryAssignment> assignments) {
        return OccupancyDetails.of(category, assignments.size(), assignments.stream()
                .mapToInt(RoomCategoryAssignment::getGuestData)
                .reduce(0, Integer::sum));
    }
}

