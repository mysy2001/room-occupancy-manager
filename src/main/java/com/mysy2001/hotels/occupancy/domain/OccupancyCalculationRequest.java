package com.mysy2001.hotels.occupancy.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OccupancyCalculationRequest {
    private int freePremiumRooms;

    private int freeEconomyRooms;

    private List<Integer> prices;
}
