package com.mysy2001.hotels.occupancy.domain.booking;

import java.util.List;

public interface BookingOrderStrategy<T> {

    BookingOrderStrategy<Integer> fromHighestPaymentBookingOrderStrategy = new FromHighestPaymentBookingOrderStrategy();

    List<T> createBookingOrder(final List<T> items);
}
