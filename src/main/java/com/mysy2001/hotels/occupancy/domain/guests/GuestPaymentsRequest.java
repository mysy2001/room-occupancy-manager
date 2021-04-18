package com.mysy2001.hotels.occupancy.domain.guests;

import java.util.List;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestPaymentsRequest {

    private List<@Min(value = 0, message = "Minimal payment value must be greater or equal 0") Integer> payments;
}
