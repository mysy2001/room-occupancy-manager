package com.mysy2001.hotels.occupancy.domain;

import java.util.ArrayList;
import java.util.List;

public class OccupancyManager {

    private static final int LOWER_PREMIUM_PRICE_LIMIT = 100;

    private final BookingOrderStrategy<Integer> bookingOrderStrategy = new FromHighestPaymentBookingOrderStrategy();

    private final GuestsDataProvider guestsDataProvider;

    public OccupancyManager() {
        this.guestsDataProvider = null;
    }

    public OccupancyManager(final GuestsDataProvider guestsDataProvider) {
        this.guestsDataProvider = guestsDataProvider;
    }

    public OccupancyCalculationResult calculateOccupancy(final OccupancyCalculationRequest request) {
        final AvailableRooms availableRooms = createAvailableRooms(request);
        return this.calculateOccupancy(availableRooms);
    }

    private AvailableRooms createAvailableRooms(final OccupancyCalculationRequest request) {
        final AvailableRooms availableRooms = new AvailableRooms();
        availableRooms.setAvailableRooms(RoomCategory.PREMIUM, request.getFreePremiumRooms());
        availableRooms.setAvailableRooms(RoomCategory.ECONOMY, request.getFreeEconomyRooms());
        return availableRooms;
    }

    private List<Integer> getGuestsData() {
        return bookingOrderStrategy.createBookingOrder(guestsDataProvider.getGuestsData());
    }

    OccupancyCalculationResult calculateOccupancy(final AvailableRooms availableRooms) {
        final List<Integer> premiumCandidates = new ArrayList<>(), economyCandidates = new ArrayList<>();
        splitPremiumAndEconomyCandidates(premiumCandidates, economyCandidates);

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

    private void splitPremiumAndEconomyCandidates(final List<Integer> premiumCandidates,
            final List<Integer> economyCandidates) {

        getGuestsData().forEach(value -> {
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

