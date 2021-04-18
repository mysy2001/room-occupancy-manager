package com.mysy2001.hotels.occupancy.domain.guests;

import java.util.List;

public interface GuestsDataProvider<T> {

    List<T> getGuestsData();
}
