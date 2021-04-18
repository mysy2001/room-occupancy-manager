package com.mysy2001.hotels.occupancy.domain.room;

import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategory;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Value
class PremiumRoom extends AbstractRoom {

    public PremiumRoom(final Price price) {
        super(price);
    }

    @Override
    public RoomCategory getCategory() {
        return RoomCategory.PREMIUM;
    }

}
