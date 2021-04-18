package com.mysy2001.hotels.occupancy.domain;

import java.util.ArrayList;
import java.util.List;

public class OccupancyManager {

    private static final int LOWER_PREMIUM_PRICE_LIMIT = 100;

    private final BookingOrderStrategy<Integer> bookingOrderStrategy = new FromHighestPaymentBookingOrderStrategy();

    public OccupancyCalculationResult calculateOccupancy(final OccupancyCalculationRequest request) {
        final AvailableRooms availableRooms = createAvailableRooms(request);
        return this.calculateOccupancy(availableRooms, request.getPrices());
    }

    private AvailableRooms createAvailableRooms(final OccupancyCalculationRequest request) {
        final AvailableRooms availableRooms = new AvailableRooms();
        availableRooms.setAvailableRooms(RoomCategory.PREMIUM, request.getFreePremiumRooms());
        availableRooms.setAvailableRooms(RoomCategory.ECONOMY, request.getFreeEconomyRooms());
        return availableRooms;
    }

    OccupancyCalculationResult calculateOccupancy(final AvailableRooms availableRooms, final List<Integer> requestedRoomPrices) {
        final List<Integer> orderedForBooking = bookingOrderStrategy.createBookingOrder(requestedRoomPrices);
        final List<Integer> premiumCandidates = new ArrayList<>(), economyCandidates = new ArrayList<>();
        splitPremiumAndEconomyCandidates(orderedForBooking, premiumCandidates, economyCandidates);

        List<Integer> premiumRooms, economyRooms;
        int premiumCandidatesCount = premiumCandidates.size();
        int economyCandidatesCount = economyCandidates.size();
        final int availablePremiumRooms = availableRooms.getAvailableRooms(RoomCategory.PREMIUM);
        final int availableEconomyRooms = availableRooms.getAvailableRooms(RoomCategory.ECONOMY);

        if ( premiumCandidatesCount < availablePremiumRooms && economyCandidatesCount > availableEconomyRooms ) {
            int availableUpgrades = availablePremiumRooms - premiumCandidatesCount;
            premiumRooms = premiumCandidates;
            premiumRooms.addAll(economyCandidates.subList(0, availableUpgrades));
            economyRooms = economyCandidates.subList(availableUpgrades, availableUpgrades + availableEconomyRooms);
        } else {
            premiumRooms = premiumCandidates.subList(0, Math.min(premiumCandidatesCount, availablePremiumRooms));
            economyRooms = economyCandidates.subList(0, Math.min(economyCandidatesCount, availableEconomyRooms));
        }

        final OccupancyDetails premiumOccupancyDetails = createOccupancyDetails(premiumRooms);
        final OccupancyDetails economyOccupancyDetails = createOccupancyDetails(economyRooms);
        return OccupancyCalculationResult.of(premiumOccupancyDetails, economyOccupancyDetails);
    }

    private void splitPremiumAndEconomyCandidates(List<Integer> priceOrderedDesc, final List<Integer> premiumCandidates,
            final List<Integer> economyCandidates) {
        priceOrderedDesc.forEach(value -> {
            if ( value >= LOWER_PREMIUM_PRICE_LIMIT ) {
                premiumCandidates.add(value);
            } else {
                economyCandidates.add(value);
            }
        });
    }

    private OccupancyDetails createOccupancyDetails(List<Integer> premiumRooms) {
        return OccupancyDetails.of(premiumRooms.size(), premiumRooms.stream()
                .reduce(0, Integer::sum));
    }
}
