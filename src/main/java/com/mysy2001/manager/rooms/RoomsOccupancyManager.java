package com.mysy2001.manager.rooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import com.mysy2001.manager.rooms.occupancy.OccupancyCalculationRequest;
import com.mysy2001.manager.rooms.occupancy.OccupancyDetails;

public class RoomsOccupancyManager {

    private static final int LOWER_PREMIUM_PRICE_LIMIT = 100;

    public RoomsOccupancyRate calculateOccupancy(final OccupancyCalculationRequest request) {
        final int[] prices = request.getPrices().stream().mapToInt(i -> i)
                .toArray();
        return this.calculateOccupancy(prices, request.getFreePremiumRooms(), request.getFreeEconomyRooms());
    }

    public RoomsOccupancyRate calculateOccupancy(final int[] requestedRoomPrices, final int freePremiumRooms, final int freeEconomyRooms) {

        final int[] priceOrderedDesc = orderPricesDescending(requestedRoomPrices);
        final List<Integer> premiumCandidates = new ArrayList<>(), economyCandidates = new ArrayList<>();
        splitPremiumAndEconomyCandidates(priceOrderedDesc, premiumCandidates, economyCandidates);

        List<Integer> premiumRooms, economyRooms;
        int premiumCandidatesCount = premiumCandidates.size();
        int economyCandidatesCount = economyCandidates.size();

        if ( premiumCandidatesCount < freePremiumRooms && economyCandidatesCount > freeEconomyRooms ) {
            int availableUpgrades = freePremiumRooms - premiumCandidatesCount;
            premiumRooms = premiumCandidates;
            premiumRooms.addAll(economyCandidates.subList(0, availableUpgrades));
            economyRooms = economyCandidates.subList(availableUpgrades, availableUpgrades + freeEconomyRooms);
        } else {
            premiumRooms = premiumCandidates.subList(0, Math.min(premiumCandidatesCount, freePremiumRooms));
            economyRooms = economyCandidates.subList(0, Math.min(economyCandidatesCount, freeEconomyRooms));
        }

        final OccupancyDetails premiumOccupancyDetails = createOccupancyDetails(premiumRooms);
        final OccupancyDetails economyOccupancyDetails = createOccupancyDetails(economyRooms);
        return RoomsOccupancyRate.of(premiumOccupancyDetails, economyOccupancyDetails);
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
