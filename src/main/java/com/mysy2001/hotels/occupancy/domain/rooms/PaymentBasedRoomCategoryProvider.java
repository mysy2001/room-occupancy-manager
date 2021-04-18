package com.mysy2001.hotels.occupancy.domain.rooms;

import lombok.Value;

@Value
public class PaymentBasedRoomCategoryProvider implements RoomCategoryProvider<Integer> {

    static final int PREMIUM_ROOM_MINIMAL_PRICE = 100;

    @Override
    public RoomCategory getRoomCategory(Integer payment) {
        if ( payment >= PREMIUM_ROOM_MINIMAL_PRICE ) {
            return RoomCategory.PREMIUM;
        } else {
            return RoomCategory.ECONOMY;
        }
    }
}
