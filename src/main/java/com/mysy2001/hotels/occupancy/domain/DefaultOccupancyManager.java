package com.mysy2001.hotels.occupancy.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mysy2001.hotels.occupancy.domain.rooms.AvailableRooms;
import com.mysy2001.hotels.occupancy.domain.rooms.DefaultRoomAssignmentsManager;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomAssignments;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomAssignmentsManager;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategory;
import com.mysy2001.hotels.occupancy.domain.rooms.RoomCategoryAssignment;

public class DefaultOccupancyManager implements OccupancyManager {

    private final RoomAssignmentsManager assignmentsManager;

    public DefaultOccupancyManager(final DefaultRoomAssignmentsManager assignmentsManager) {
        this.assignmentsManager = assignmentsManager;
    }

    @Override
    public OccupancyCalculationResult calculateOccupancy(final AvailableRooms rooms) {
        final RoomAssignments assignments = this.assignmentsManager.getRoomAssignments(rooms);
        return createResult(assignments);
    }

    private OccupancyCalculationResult createResult(final RoomAssignments assignments) {
        final List<OccupancyDetails> occupancyDetails = Arrays.stream(RoomCategory.values())
                .map(category -> {
                    final List<RoomCategoryAssignment> categoryAssignments = assignments.getAssignments(category);
                    return createOccupancyDetails(category, categoryAssignments);
                })
                .collect(Collectors.toList());
        return OccupancyCalculationResult.of(occupancyDetails);
    }

    private OccupancyDetails createOccupancyDetails(final RoomCategory category, final List<RoomCategoryAssignment> assignments) {
        return OccupancyDetails.of(category, assignments.size(), assignments.stream()
                .mapToInt(RoomCategoryAssignment::getGuestData)
                .reduce(0, Integer::sum));
    }
}

