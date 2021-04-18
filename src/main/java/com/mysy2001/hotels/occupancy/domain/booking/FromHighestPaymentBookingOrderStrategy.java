package com.mysy2001.hotels.occupancy.domain.booking;

import java.util.List;
import java.util.stream.Collectors;

class FromHighestPaymentBookingOrderStrategy implements BookingOrderStrategy<Integer> {

    @Override
    public List<Integer> createBookingOrder(List<Integer> items) {
        return items.stream()
                .sorted((o1, o2) -> Integer.compare(o2, o1))
                .collect(Collectors.toList());
    }
}
