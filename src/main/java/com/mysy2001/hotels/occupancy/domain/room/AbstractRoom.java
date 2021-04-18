package com.mysy2001.hotels.occupancy.domain.room;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
abstract class AbstractRoom implements Room {

    private final Price price;

    AbstractRoom(final Price price) {
        this.price = price;
    }

    @Override
    public Price getPrice() {
        return price;
    }
}
