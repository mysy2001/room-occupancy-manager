package com.mysy2001.hotels.occupancy.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

class RoomsOccupancyManagerTest {

//    private final List<Integer> requestedRoomPrices = List.of(23, 45, 155, 374, 22, 99, 100, 101, 115, 209);

    private static List<Integer> POTENTIAL_GUESTS;
    private final OccupancyManager objectUnderTest = new OccupancyManager();

    private static AvailableRooms createAvailableRooms(final int freePremiumRooms, final int freeEconomyRooms) {
        final AvailableRooms availableRooms = new AvailableRooms();
        availableRooms.setAvailableRooms(RoomCategory.PREMIUM, freePremiumRooms);
        availableRooms.setAvailableRooms(RoomCategory.ECONOMY, freeEconomyRooms);
        return availableRooms;
    }

    @BeforeAll
    static void setUpAll() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream is = RoomsOccupancyManagerTest.class.getResourceAsStream("/potential-guests.json");
        POTENTIAL_GUESTS = objectMapper.readValue(is, new TypeReference<>() {});
    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_3_premium_rooms_and_3_economy_rooms() {

        final AvailableRooms availableRooms = createAvailableRooms(3, 3);

        final OccupancyCalculationResult result = objectUnderTest.calculateOccupancy(availableRooms, POTENTIAL_GUESTS);

        final OccupancyCalculationResult expected = OccupancyCalculationResult.of(OccupancyDetails.of(3, 738), OccupancyDetails.of(3, 167));

        assertThat(result).isEqualTo(expected);

    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_7_premium_rooms_and_5_economy_rooms() {

        final AvailableRooms availableRooms = createAvailableRooms(7, 5);

        final OccupancyCalculationResult result = objectUnderTest.calculateOccupancy(availableRooms, POTENTIAL_GUESTS);

        final OccupancyCalculationResult expected = OccupancyCalculationResult.of(OccupancyDetails.of(6, 1054), OccupancyDetails.of(4, 189));

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_2_premium_rooms_and_7_economy_rooms() {

        final AvailableRooms availableRooms = createAvailableRooms(2, 7);

        final OccupancyCalculationResult result = objectUnderTest.calculateOccupancy(availableRooms, POTENTIAL_GUESTS);

        final OccupancyCalculationResult expected = OccupancyCalculationResult.of(OccupancyDetails.of(2, 583), OccupancyDetails.of(4, 189));

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_7_premium_rooms_and_1_economy_room() {

        final AvailableRooms availableRooms = createAvailableRooms(7, 1);

        final OccupancyCalculationResult result = objectUnderTest.calculateOccupancy(availableRooms, POTENTIAL_GUESTS);

        final OccupancyCalculationResult expected = OccupancyCalculationResult.of(OccupancyDetails.of(7, 1153), OccupancyDetails.of(1, 45));

        assertThat(result).isEqualTo(expected);
    }
}
