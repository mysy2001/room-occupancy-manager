package com.mysy2001.hotels.occupancy.domain;

import java.util.List;

interface BookingOrderStrategy<T> {

    List<T> createBookingOrder(final List<T> items);
}
