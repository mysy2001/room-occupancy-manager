package com.mysy2001.manager.rooms;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import lombok.Value;

class RoomsOccupancyManagerTest {

    private final int[] requestedRoomPrices = new int[] { 23, 45, 155, 374, 22, 99, 100, 101, 115, 209 };

    private final RoomsOccupancyManager objectUnderTest = new RoomsOccupancyManager();

    @Test
    void should_calculate_rooms_occupancy_when_available_are_3_premium_rooms_and_3_economy_rooms() {

        final int freePremiumRooms = 3;
        final int freeEconomyRooms = 3;

        final RoomsOccupancyRate result = objectUnderTest.calculateOccupancyRate(requestedRoomPrices, freePremiumRooms, freeEconomyRooms);

        final RoomsOccupancyRate expected = RoomsOccupancyRate.of(3, 738, 3, 167);

        assertThat(result).isEqualTo(expected);

    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_7_premium_rooms_and_5_economy_rooms() {

        final int freePremiumRooms = 7;
        final int freeEconomyRooms = 5;

        final RoomsOccupancyRate result = objectUnderTest.calculateOccupancyRate(requestedRoomPrices, freePremiumRooms, freeEconomyRooms);

        final RoomsOccupancyRate expected = RoomsOccupancyRate.of(6, 1054, 4, 189);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_2_premium_rooms_and_7_economy_rooms() {
        final int freePremiumRooms = 2;
        final int freeEconomyRooms = 7;

        final RoomsOccupancyRate result = objectUnderTest.calculateOccupancyRate(requestedRoomPrices, freePremiumRooms, freeEconomyRooms);

        final RoomsOccupancyRate expected = RoomsOccupancyRate.of(2, 583, 4, 189);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_7_premium_rooms_and_1_economy_room() {
        final int freePremiumRooms = 7;
        final int freeEconomyRooms = 1;

        final RoomsOccupancyRate result = objectUnderTest.calculateOccupancyRate(requestedRoomPrices, freePremiumRooms, freeEconomyRooms);

        final RoomsOccupancyRate expected = RoomsOccupancyRate.of(7, 1153, 1, 45);

        assertThat(result).isEqualTo(expected);
    }
}

@Value(staticConstructor = "of")
class RoomsOccupancyRate {

    int premiumRoomsUsage;

    int premiumRoomsPrice;

    int economyRoomsUsage;

    int economyRoomsPrice;

}

class RoomsOccupancyManager {

    private static final int LOWER_PREMIUM_PRICE_LIMIT = 100;

    RoomsOccupancyRate calculateOccupancyRate(final int[] requestedRoomPrices, final int freePremiumRooms, final int freeEconomyRooms) {

        final int[] priceOrderedDesc = orderPricesDescending(requestedRoomPrices);
        final List<Integer> premiumCandidates = new ArrayList<>(), economyCandidates = new ArrayList<>();
        splitPremiumAndEconomyCandidates(priceOrderedDesc, premiumCandidates, economyCandidates);

        List<Integer> premiumRooms, economyRooms;
        int premiumCandidatesCount = premiumCandidates.size();
        int economyCandidatesCount = economyCandidates.size();

        if (premiumCandidatesCount < freePremiumRooms && economyCandidatesCount > freeEconomyRooms) {
            int availableUpgrades = freePremiumRooms - premiumCandidatesCount;
            premiumRooms = premiumCandidates;
            premiumRooms.addAll(economyCandidates.subList(0, availableUpgrades));
            economyRooms = economyCandidates.subList(availableUpgrades, availableUpgrades + freeEconomyRooms);
        } else {
            premiumRooms = premiumCandidates.subList(0, Math.min(premiumCandidatesCount, freePremiumRooms));
            economyRooms = economyCandidates.subList(0, Math.min(economyCandidatesCount, freeEconomyRooms));

        }
        return RoomsOccupancyRate.of(premiumRooms.size(), premiumRooms.stream()
                .reduce(0, Integer::sum), economyRooms.size(), economyRooms.stream()
                .reduce(0, Integer::sum));
    }

    private void splitPremiumAndEconomyCandidates(int[] priceOrderedDesc, final List<Integer> premiumCandidates, final List<Integer> economyCandidates) {
        Arrays.stream(priceOrderedDesc)
                .forEachOrdered(value -> {
                    if (value >= LOWER_PREMIUM_PRICE_LIMIT) {
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
}
