package com.mysy2001.hotels.occupancy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysy2001.hotels.occupancy.domain.guests.GuestsDataProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PotentialGuestsDataProviderStub implements GuestsDataProvider<Integer> {

    private final List<Integer> guestsData;

    public PotentialGuestsDataProviderStub() {
        List<Integer> loadedGuestsData;
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream is = PotentialGuestsDataProviderStub.class.getResourceAsStream("/potential-guests.json");
        try {
            loadedGuestsData = objectMapper.readValue(is, new TypeReference<>() {
            });
        } catch (IOException e) {
            loadedGuestsData = Collections.emptyList();
        }
        this.guestsData = loadedGuestsData;
    }

    @Override
    public List<Integer> getGuestsData() {
        return guestsData;
    }
}
