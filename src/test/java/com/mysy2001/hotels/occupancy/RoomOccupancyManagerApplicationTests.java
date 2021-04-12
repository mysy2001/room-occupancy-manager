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

@Disabled
@SpringBootTest(classes = RoomOccupancyManagerConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoomOccupancyManagerApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    static Stream<Arguments> createParameters() {
        return Stream.of(Arguments.of(new OccupancyCalculationRequest(3, 3, List.of(23, 45, 155, 374, 22, 99, 100, 101, 115, 209)),
                OccupancyCalculationResult.of(OccupancyDetails.of(3, 738), OccupancyDetails.of(3, 167))));
    }

    @ParameterizedTest
    @MethodSource("createParameters")
    void should_receive_expected_response(final OccupancyCalculationRequest body, final OccupancyCalculationResult expected) {
        final OccupancyCalculationResult result = restTemplate.postForObject("http://localhost:" + port + "/occupancy/calculation", body,
                OccupancyCalculationResult.class);

        assertThat(result).isEqualTo(expected);

    }

}
