package com.mysy2001.hotels.occupancy.domain.rooms;

import java.util.EnumMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class AvailableRooms {

    private final Map<RoomCategory, Integer> rooms;

    public AvailableRooms() {
        this.rooms = new EnumMap<>(RoomCategory.class);
    }

    public AvailableRooms withPremiumRooms(final int amount) {
        this.setAvailableRooms(RoomCategory.PREMIUM, amount);
        return this;
    }

    public AvailableRooms withEconomyRooms(final int amount) {
        this.setAvailableRooms(RoomCategory.ECONOMY, amount);
        return this;
    }

    public int getEconomyRooms() {
        return this.getAvailableRooms(RoomCategory.ECONOMY);
    }

    public int getPremiumRooms() {
        return this.getAvailableRooms(RoomCategory.PREMIUM);
    }

    private void setAvailableRooms(final RoomCategory category, int amount) {
        this.rooms.put(category, amount);
    }

    private int getAvailableRooms(final RoomCategory category) {
        return this.rooms.getOrDefault(category, 0);
    }
}
