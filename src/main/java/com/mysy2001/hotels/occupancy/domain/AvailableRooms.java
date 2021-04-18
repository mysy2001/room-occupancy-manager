package com.mysy2001.hotels.occupancy.domain;

import java.util.EnumMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class AvailableRooms {

    private Map<RoomCategory, Integer> rooms;

    public AvailableRooms() {
        this.rooms = new EnumMap<>(RoomCategory.class);
    }

    public void setAvailableRooms(final RoomCategory category, int amount) {
        this.rooms.put(category, amount);
    }

    public int getAvailableRooms(final RoomCategory category) {
        return this.rooms.getOrDefault(category, 0);
    }
}
