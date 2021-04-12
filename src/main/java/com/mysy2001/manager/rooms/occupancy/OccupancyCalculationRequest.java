package com.mysy2001.manager.rooms.occupancy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OccupancyCalculationRequest {
    private int freePremiumRooms;

    private int freeEconomyRooms;

    private List<Integer> prices;
}
