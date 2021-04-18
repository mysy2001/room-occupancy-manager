package com.mysy2001.hotels.occupancy.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class OccupancyManager {

    private static final int LOWER_PREMIUM_PRICE_LIMIT = 100;

    public OccupancyCalculationResult calculateOccupancy(final OccupancyCalculationRequest request) {
        final int[] prices = request.getPrices()
                .stream()
                .mapToInt(i -> i)
                .toArray();

        final AvailableRooms availableRooms = createAvailableRooms(request);
        return this.calculateOccupancy(availableRooms, prices);
    }

    private AvailableRooms createAvailableRooms(final OccupancyCalculationRequest request) {
        final AvailableRooms availableRooms = new AvailableRooms();
        availableRooms.setAvailableRooms(RoomCategory.PREMIUM, request.getFreePremiumRooms());
        availableRooms.setAvailableRooms(RoomCategory.ECONOMY, request.getFreeEconomyRooms());
        return availableRooms;
    }

    OccupancyCalculationResult calculateOccupancy(final AvailableRooms availableRooms, final int... requestedRoomPrices) {
        final int[] priceOrderedDesc = orderPricesDescending(requestedRoomPrices);
        final List<Integer> premiumCandidates = new ArrayList<>(), economyCandidates = new ArrayList<>();
        splitPremiumAndEconomyCandidates(priceOrderedDesc, premiumCandidates, economyCandidates);

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

    private void splitPremiumAndEconomyCandidates(int[] priceOrderedDesc, final List<Integer> premiumCandidates, final List<Integer> economyCandidates) {
        Arrays.stream(priceOrderedDesc)
                .forEach(value -> {
                    if ( value >= LOWER_PREMIUM_PRICE_LIMIT ) {
                        premiumCandidates.add(value);
                    } else {
                        economyCandidates.add(value);
                    }
                });
    }

    private int[] orderPricesDescending(final int[] requestedRoomPrices) {
        return IntStream.of(requestedRoomPrices)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(i -> i)
                .toArray();
    }

    private OccupancyDetails createOccupancyDetails(List<Integer> premiumRooms) {
        return OccupancyDetails.of(premiumRooms.size(), premiumRooms.stream()
                .reduce(0, Integer::sum));
    }
}
