package com.mysy2001.hotels.occupancy.domain;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OccupancyCalculationResult {

    private List<OccupancyDetails> occupancy;

    public static OccupancyCalculationResult of(OccupancyDetails... occupancy) {
        return new OccupancyCalculationResult(Arrays.asList(occupancy));
    }

}
