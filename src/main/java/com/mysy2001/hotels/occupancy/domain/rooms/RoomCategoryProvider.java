package com.mysy2001.hotels.occupancy.domain.rooms;

@FunctionalInterface
public interface RoomCategoryProvider<T> {

    RoomCategoryProvider<Integer> paymentBasedRoomCategoryProvider = new PaymentBasedRoomCategoryProvider();

    RoomCategory getRoomCategory(T item);
}
