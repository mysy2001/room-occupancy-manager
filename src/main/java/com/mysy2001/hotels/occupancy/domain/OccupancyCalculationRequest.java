package com.mysy2001.hotels.occupancy.domain;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OccupancyCalculationRequest {

    @Min(value = 0, message = "Amount of free premium rooms must be > 0")
    private int freePremiumRooms;

    @Min(value = 0, message = "Amount of free economy rooms must be > 0")
    private int freeEconomyRooms;
}
