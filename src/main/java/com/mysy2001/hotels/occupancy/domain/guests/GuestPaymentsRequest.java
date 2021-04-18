package com.mysy2001.hotels.occupancy.domain.guests;

import java.util.List;

import lombok.Data;

@Data
public class GuestPaymentsRequest {

    private List<Integer> payments;
}
