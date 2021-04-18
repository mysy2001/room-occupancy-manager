package com.mysy2001.hotels.occupancy.domain;

@FunctionalInterface
interface RoomCategoryProvider<T> {

    RoomCategory getRoomCategory(T item);
}
