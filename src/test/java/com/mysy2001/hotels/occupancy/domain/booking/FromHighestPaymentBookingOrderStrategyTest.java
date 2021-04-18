package com.mysy2001.hotels.occupancy.domain.booking;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

class FromHighestPaymentBookingOrderStrategyTest {
    @Test
    void should_return_items_ordered_descending() {

        final FromHighestPaymentBookingOrderStrategy objectUnderTest = new FromHighestPaymentBookingOrderStrategy();

        final Collection<Integer> result = objectUnderTest.createBookingOrder(List.of(1, 2, 3, 4));

        assertThat(result).containsExactly(4, 3, 2, 1);

    }

}
