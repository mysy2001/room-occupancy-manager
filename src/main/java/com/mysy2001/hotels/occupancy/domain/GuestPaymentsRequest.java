package com.mysy2001.hotels.occupancy.domain;

import java.util.List;

import lombok.Data;

@Data
public class GuestPaymentsRequest {

    private List<Integer> payments;
}
