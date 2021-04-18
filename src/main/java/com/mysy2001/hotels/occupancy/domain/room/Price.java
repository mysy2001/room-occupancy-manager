package com.mysy2001.hotels.occupancy.domain.room;

import lombok.Value;

@Value
public class Price {
    int value;

    String currencyCode;

    Price ofEuro(final int value) {
        return new Price(value, "EUR");
    }
}
