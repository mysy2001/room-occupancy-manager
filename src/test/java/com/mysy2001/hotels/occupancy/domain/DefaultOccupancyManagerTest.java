package com.mysy2001.hotels.occupancy.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mysy2001.hotels.occupancy.PotentialGuestsDataProviderStub;
import com.mysy2001.hotels.occupancy.domain.booking.BookingOrderStrategy;
import com.mysy2001.hotels.occupancy.domain.booking.BookingWithUpgradeStrategy;
import com.mysy2001.hotels.occupancy.domain.guests.GuestsDataProvider;
import com.mysy2001.hotels.occupancy.domain.rooms.AvailableRooms;
import com.mysy2001.hotels.occupancy.domain.rooms.DefaultRoomAssignmentsManager;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategory;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategoryProvider;

class DefaultOccupancyManagerTest {

    private DefaultOccupancyManager objectUnderTest;

    private final GuestsDataProvider<Integer> potentialGuestsDataProvider = new PotentialGuestsDataProviderStub();

    private static AvailableRooms createAvailableRooms(final int freePremiumRooms, final int freeEconomyRooms) {
        return new AvailableRooms().withPremiumRooms(freePremiumRooms)
                .withEconomyRooms(freeEconomyRooms);
    }

    @BeforeEach
    void setUp() {
        final DefaultRoomAssignmentsManager roomAssignmentsManager = new DefaultRoomAssignmentsManager(potentialGuestsDataProvider,
                BookingOrderStrategy.fromHighestPaymentBookingOrderStrategy, RoomCategoryProvider.paymentBasedRoomCategoryProvider);
        this.objectUnderTest = new DefaultOccupancyManager(roomAssignmentsManager, new BookingWithUpgradeStrategy());
    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_3_premium_rooms_and_3_economy_rooms() {

        final AvailableRooms availableRooms = createAvailableRooms(3, 3);

        final OccupancyCalculationResult result = objectUnderTest.calculateOccupancy(availableRooms);

        final OccupancyCalculationResult expected = OccupancyCalculationResult.of(OccupancyDetails.of(RoomCategory.PREMIUM, 3, 738),
                OccupancyDetails.of(RoomCategory.ECONOMY, 3, 167));

        assertThat(result).isEqualTo(expected);

    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_7_premium_rooms_and_5_economy_rooms() {

        final AvailableRooms availableRooms = createAvailableRooms(7, 5);

        final OccupancyCalculationResult result = objectUnderTest.calculateOccupancy(availableRooms);

        final OccupancyCalculationResult expected = OccupancyCalculationResult.of(OccupancyDetails.of(RoomCategory.PREMIUM, 6, 1054),
                OccupancyDetails.of(RoomCategory.ECONOMY, 4, 189));

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_2_premium_rooms_and_7_economy_rooms() {

        final AvailableRooms availableRooms = createAvailableRooms(2, 7);

        final OccupancyCalculationResult result = objectUnderTest.calculateOccupancy(availableRooms);

        final OccupancyCalculationResult expected = OccupancyCalculationResult.of(OccupancyDetails.of(RoomCategory.PREMIUM, 2, 583),
                OccupancyDetails.of(RoomCategory.ECONOMY, 4, 189));

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_calculate_rooms_occupancy_when_available_are_7_premium_rooms_and_1_economy_room() {

        final AvailableRooms availableRooms = createAvailableRooms(7, 1);

        final OccupancyCalculationResult result = objectUnderTest.calculateOccupancy(availableRooms);

        final OccupancyCalculationResult expected = OccupancyCalculationResult.of(OccupancyDetails.of(RoomCategory.PREMIUM, 7, 1153),
                OccupancyDetails.of(RoomCategory.ECONOMY, 1, 45));

        assertThat(result).isEqualTo(expected);
    }
}

