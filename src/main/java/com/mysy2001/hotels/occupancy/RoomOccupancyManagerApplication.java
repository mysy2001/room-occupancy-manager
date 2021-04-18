package com.mysy2001.hotels.occupancy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mysy2001.hotels.occupancy.domain.GuestPaymentsDataManager;
import com.mysy2001.hotels.occupancy.domain.GuestsDataProvider;
import com.mysy2001.hotels.occupancy.domain.OccupancyManager;

@SpringBootApplication
public class RoomOccupancyManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomOccupancyManagerApplication.class, args);
    }

}

@Configuration
class RoomOccupancyManagerConfiguration {

    @Bean
    public OccupancyManager roomsOccupancyManager(final GuestsDataProvider guestsDataProvider) {
        return new OccupancyManager(guestsDataProvider);
    }

    @Bean
    public GuestsDataProvider guestsDataManager() {
        return new GuestPaymentsDataManager();
    }
}