package com.mysy2001.hotels.occupancy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mysy2001.hotels.occupancy.domain.OccupancyCalculationRequest;
import com.mysy2001.hotels.occupancy.domain.OccupancyCalculationResult;
import com.mysy2001.hotels.occupancy.domain.OccupancyDetails;
import com.mysy2001.hotels.occupancy.domain.guests.GuestPaymentsRequest;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategory;

@SpringBootTest(classes = RoomOccupancyManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoomOccupancyManagerApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    static Stream<Arguments> createParameters() {
        return Stream.of(Arguments.of(new OccupancyCalculationRequest(3, 3),
                new OccupancyDetails[] { OccupancyDetails.of(RoomCategory.PREMIUM, 3, 738), OccupancyDetails.of(RoomCategory.ECONOMY, 3, 167) }),
                Arguments.of(new OccupancyCalculationRequest(7, 5),
                        new OccupancyDetails[] { OccupancyDetails.of(RoomCategory.PREMIUM, 6, 1054), OccupancyDetails.of(RoomCategory.ECONOMY, 4, 189) }),
                Arguments.of(new OccupancyCalculationRequest(2, 7),
                        new OccupancyDetails[] { OccupancyDetails.of(RoomCategory.PREMIUM, 2, 583), OccupancyDetails.of(RoomCategory.ECONOMY, 4, 189) }),
                Arguments.of(new OccupancyCalculationRequest(7, 1),
                        new OccupancyDetails[] { OccupancyDetails.of(RoomCategory.PREMIUM, 7, 1153), OccupancyDetails.of(RoomCategory.ECONOMY, 1, 45) }));
    }

    @BeforeEach
    void setUp() {
        final List<Integer> guestsData = new PotentialGuestsDataProviderStub().getGuestsData();
        final GuestPaymentsRequest request = new GuestPaymentsRequest(guestsData);
        restTemplate.postForEntity("http://localhost:" + port + "/occupancy/guests/payments", request, Void.class);
    }

    @ParameterizedTest
    @MethodSource("createParameters")
    void should_receive_expected_response(final OccupancyCalculationRequest body, final OccupancyDetails... expected) {
        final OccupancyCalculationResult result = restTemplate.postForObject("http://localhost:" + port + "/occupancy/calculation", body,
                OccupancyCalculationResult.class);

        assertThat(result).isNotNull();
        assertThat(result.getOccupancy()).containsExactlyInAnyOrder(expected);

    }

    @Test
    void should_return_http_400_bad_request_when_OccupancyCalculationRequest_freePremiumRooms_is_lower_than_zero() {

        final OccupancyCalculationRequest request = new OccupancyCalculationRequest(-1, 3);
        final ResponseEntity<OccupancyCalculationResult> result = restTemplate.postForEntity("http://localhost:" + port + "/occupancy/calculation", request,
                OccupancyCalculationResult.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void should_return_http_400_bad_request_when_OccupancyCalculationRequest_freeEconomyRooms_is_lower_than_zero() {

        final OccupancyCalculationRequest request = new OccupancyCalculationRequest(3, -1);
        final ResponseEntity<OccupancyCalculationResult> result = restTemplate.postForEntity("http://localhost:" + port + "/occupancy/calculation", request,
                OccupancyCalculationResult.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void should_return_http_400_bad_request_when_GuestPaymentsRequest_payments_contains_value_lower_than_zero() {

        final GuestPaymentsRequest request = new GuestPaymentsRequest(List.of(1,2,-11));
        final ResponseEntity<Void> result = restTemplate.postForEntity("http://localhost:" + port + "/occupancy/guests/payments", request, Void.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
