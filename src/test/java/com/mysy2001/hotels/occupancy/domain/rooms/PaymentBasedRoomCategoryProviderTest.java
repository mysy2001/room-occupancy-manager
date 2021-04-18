package com.mysy2001.hotels.occupancy.domain.rooms;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentBasedRoomCategoryProviderTest {

    private PaymentBasedRoomCategoryProvider objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new PaymentBasedRoomCategoryProvider();
    }

    @Test
    void should_return_premium_category_when_customer_willing_to_pay_more_than_100_EUR() {

        final RoomCategory result = objectUnderTest.getRoomCategory(101);

        assertThat(result).isEqualTo(RoomCategory.PREMIUM);
    }

    @Test
    void should_return_premium_category_when_customer_willing_to_pay_100_EUR() {

        final RoomCategory result = objectUnderTest.getRoomCategory(100);

        assertThat(result).isEqualTo(RoomCategory.PREMIUM);
    }

    @Test
    void should_return_economy_category_when_customer_willing_to_pay_less_than_100_EUR() {
        final RoomCategory result = objectUnderTest.getRoomCategory(99);

        assertThat(result).isEqualTo(RoomCategory.ECONOMY);
    }

}
