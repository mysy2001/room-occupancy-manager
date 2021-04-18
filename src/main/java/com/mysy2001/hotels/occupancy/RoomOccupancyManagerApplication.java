package com.mysy2001.hotels.occupancy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mysy2001.hotels.occupancy.domain.DefaultOccupancyManager;
import com.mysy2001.hotels.occupancy.domain.booking.BookingWithUpgradeStrategy;
import com.mysy2001.hotels.occupancy.domain.rooms.DefaultRoomAssignmentsManager;
import com.mysy2001.hotels.occupancy.domain.OccupancyManager;
import com.mysy2001.hotels.occupancy.domain.booking.BookingOrderStrategy;
import com.mysy2001.hotels.occupancy.domain.guests.GuestPaymentsDataManager;
import com.mysy2001.hotels.occupancy.domain.guests.GuestsDataProvider;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategoryProvider;

@SpringBootApplication
public class RoomOccupancyManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomOccupancyManagerApplication.class, args);
    }

}

@Configuration
class RoomOccupancyManagerConfiguration {

    @Bean
    public OccupancyManager roomsOccupancyManager(final GuestsDataProvider<Integer> guestsDataProvider) {

        final DefaultRoomAssignmentsManager roomAssignmentsManager = new DefaultRoomAssignmentsManager(guestsDataProvider,
                BookingOrderStrategy.fromHighestPaymentBookingOrderStrategy, RoomCategoryProvider.paymentBasedRoomCategoryProvider);
        return new DefaultOccupancyManager(roomAssignmentsManager, new BookingWithUpgradeStrategy());
    }

    @Bean
    public GuestsDataProvider<Integer> guestsDataManager() {
        return new GuestPaymentsDataManager();
    }
}