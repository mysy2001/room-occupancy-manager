package com.mysy2001.hotels.occupancy.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mysy2001.hotels.occupancy.domain.guests.GuestsDataProvider;

public class OccupancyManager {

    private final BookingOrderStrategy<Integer> bookingOrderStrategy = new FromHighestPaymentBookingOrderStrategy();

    private final GuestsDataProvider<Integer> guestsDataProvider;

    private final RoomCategoryProvider<Integer> roomCategoryProvider = new PaymentBasedRoomCategoryProvider();

    public OccupancyManager(final GuestsDataProvider<Integer> guestsDataProvider) {
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
        final RoomAssignments assignments = getGuestsRoomAssignments();
        final RoomAssignments bookingAssignments = assignments.selectForBooking(availableRooms);
        return createResult(bookingAssignments);
    }

    private RoomAssignments getGuestsRoomAssignments() {
        final RoomAssignments assignments = new RoomAssignments();
        getGuestsData().forEach(integer -> {
            final RoomCategory category = roomCategoryProvider.getRoomCategory(integer);
            assignments.append(new RoomCategoryAssignment(category, integer));
        });
        return assignments;
    }

    private List<Integer> getGuestsData() {
        return bookingOrderStrategy.createBookingOrder(guestsDataProvider.getGuestsData());
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

