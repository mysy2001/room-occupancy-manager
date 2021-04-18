package com.mysy2001.hotels.occupancy.domain.guests;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestPaymentsRequest {

    private List<Integer> payments;
}
