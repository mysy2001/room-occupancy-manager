package com.mysy2001.manager.rooms;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.mysy2001.manager.rooms.occupancy.OccupancyDetails;

class RoomsOccupancyManagerTest {

    private final int[] requestedRoomPrices = new int[] { 23, 45, 155, 374, 22, 99, 100, 101, 115, 209 };

    private final RoomsOccupancyManager objectUnderTest = new RoomsOccupancyManager();

    @Test
    void should_calculate_rooms_occupancy_when_available_are_3_premium_rooms_and_3_economy_rooms() {

        final int freePremiumRooms = 3;
        final int freeEconomyRooms = 3;

        final RoomsOccupancyRate result = objectUnderTest.calculateOccupancy(requestedRoomPrices, freePremiumRooms, freeEconomyRooms);

        final RoomsOccupancyRate expected = RoomsOccupancyRate.of(OccupancyDetails.of(3, 738), OccupancyDetails.of(3, 167));

        assertThat(result).isEqualTo(expected);

    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_7_premium_rooms_and_5_economy_rooms() {

        final int freePremiumRooms = 7;
        final int freeEconomyRooms = 5;

        final RoomsOccupancyRate result = objectUnderTest.calculateOccupancy(requestedRoomPrices, freePremiumRooms, freeEconomyRooms);

        final RoomsOccupancyRate expected = RoomsOccupancyRate.of(OccupancyDetails.of(6, 1054), OccupancyDetails.of(4, 189));

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_2_premium_rooms_and_7_economy_rooms() {
        final int freePremiumRooms = 2;
        final int freeEconomyRooms = 7;

        final RoomsOccupancyRate result = objectUnderTest.calculateOccupancy(requestedRoomPrices, freePremiumRooms, freeEconomyRooms);

        final RoomsOccupancyRate expected = RoomsOccupancyRate.of(OccupancyDetails.of(2, 583), OccupancyDetails.of(4, 189));

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_7_premium_rooms_and_1_economy_room() {
        final int freePremiumRooms = 7;
        final int freeEconomyRooms = 1;

        final RoomsOccupancyRate result = objectUnderTest.calculateOccupancy(requestedRoomPrices, freePremiumRooms, freeEconomyRooms);

        final RoomsOccupancyRate expected = RoomsOccupancyRate.of(OccupancyDetails.of(7, 1153), OccupancyDetails.of(1, 45));

        assertThat(result).isEqualTo(expected);
    }
}

