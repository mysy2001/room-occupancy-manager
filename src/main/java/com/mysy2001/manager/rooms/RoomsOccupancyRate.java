package com.mysy2001.manager.rooms;

import lombok.Value;

@Value(staticConstructor = "of")
public class RoomsOccupancyRate {

    int premiumRoomsUsage;

    int premiumRoomsPrice;

    int economyRoomsUsage;

    int economyRoomsPrice;

}
