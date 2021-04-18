package com.mysy2001.hotels.occupancy.domain;

import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OccupancyDetails {

    RoomCategory category;

    int usage;

    int price;

    public static OccupancyDetails of(final RoomCategory category, final int usage, final int price) {
        return new OccupancyDetails(category, usage, price);
    }
}
