package com.mysy2001.hotels.occupancy.domain.guests;

import java.util.LinkedList;
import java.util.List;

public class GuestPaymentsDataManager implements GuestsDataProvider<Integer> {

    private final List<Integer> payments;

    public GuestPaymentsDataManager() {
        payments = new LinkedList<>();
    }

    public void setGuestsData(final GuestPaymentsRequest request) {
        this.payments.clear();
        this.payments.addAll(request.getPayments());
    }

    @Override
    public List<Integer> getGuestsData() {
        return List.copyOf(payments);
    }
}
