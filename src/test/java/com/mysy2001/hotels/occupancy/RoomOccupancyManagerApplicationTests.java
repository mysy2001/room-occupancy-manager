package com.mysy2001.hotels.occupancy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.mysy2001.hotels.occupancy.domain.OccupancyCalculationRequest;
import com.mysy2001.hotels.occupancy.domain.OccupancyCalculationResult;
import com.mysy2001.hotels.occupancy.domain.OccupancyDetails;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategory;

@Disabled
@SpringBootTest(classes = RoomOccupancyManagerConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoomOccupancyManagerApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    static Stream<Arguments> createParameters() {
        return Stream.of(Arguments.of(new OccupancyCalculationRequest(3, 3),
                OccupancyCalculationResult.of(List.of(OccupancyDetails.of(RoomCategory.PREMIUM, 3, 738), OccupancyDetails.of(RoomCategory.ECONOMY, 3, 167)))));
    }

    @ParameterizedTest
    @MethodSource("createParameters")
    void should_receive_expected_response(final OccupancyCalculationRequest body, final OccupancyCalculationResult expected) {
        final OccupancyCalculationResult result = restTemplate.postForObject("http://localhost:" + port + "/occupancy/calculation", body,
                OccupancyCalculationResult.class);

        assertThat(result).isEqualTo(expected);

    }

}
