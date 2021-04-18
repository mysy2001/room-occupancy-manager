package com.mysy2001.hotels.occupancy.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

class RoomsOccupancyManagerTest {

    private static List<Integer> POTENTIAL_GUESTS;

    private OccupancyManager objectUnderTest;

    private GuestsDataProvider potentialGuestsDataProvider;

    private static AvailableRooms createAvailableRooms(final int freePremiumRooms, final int freeEconomyRooms) {
        return new AvailableRooms()
                .withPremiumRooms(freePremiumRooms)
                .withEconomyRooms(freeEconomyRooms);
    }

    @BeforeAll
    static void setUpAll() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream is = RoomsOccupancyManagerTest.class.getResourceAsStream("/potential-guests.json");
        POTENTIAL_GUESTS = objectMapper.readValue(is, new TypeReference<>() {
        });
    }

    @BeforeEach
    void setUp() {
        this.potentialGuestsDataProvider = new PotentialGuestsDataProviderStub(POTENTIAL_GUESTS);
        this.objectUnderTest = new OccupancyManager(potentialGuestsDataProvider);
    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_3_premium_rooms_and_3_economy_rooms() {

        final AvailableRooms availableRooms = createAvailableRooms(3, 3);

        final OccupancyCalculationResult result = objectUnderTest.calculateOccupancy(availableRooms);

        final OccupancyCalculationResult expected = OccupancyCalculationResult.of(OccupancyDetails.of(3, 738), OccupancyDetails.of(3, 167));

        assertThat(result).isEqualTo(expected);

    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_7_premium_rooms_and_5_economy_rooms() {

        final AvailableRooms availableRooms = createAvailableRooms(7, 5);

        final OccupancyCalculationResult result = objectUnderTest.calculateOccupancy(availableRooms);

        final OccupancyCalculationResult expected = OccupancyCalculationResult.of(OccupancyDetails.of(6, 1054), OccupancyDetails.of(4, 189));

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_2_premium_rooms_and_7_economy_rooms() {

        final AvailableRooms availableRooms = createAvailableRooms(2, 7);

        final OccupancyCalculationResult result = objectUnderTest.calculateOccupancy(availableRooms);

        final OccupancyCalculationResult expected = OccupancyCalculationResult.of(OccupancyDetails.of(2, 583), OccupancyDetails.of(4, 189));

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_7_premium_rooms_and_1_economy_room() {

        final AvailableRooms availableRooms = createAvailableRooms(7, 1);

        final OccupancyCalculationResult result = objectUnderTest.calculateOccupancy(availableRooms);

        final OccupancyCalculationResult expected = OccupancyCalculationResult.of(OccupancyDetails.of(7, 1153), OccupancyDetails.of(1, 45));

        assertThat(result).isEqualTo(expected);
    }
}

@RequiredArgsConstructor
class PotentialGuestsDataProviderStub implements GuestsDataProvider {

    private final List<Integer> guestsData;

    @Override
    public List<Integer> getGuestsData() {
        return guestsData;
    }
}