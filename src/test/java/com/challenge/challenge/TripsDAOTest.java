package com.challenge.challenge;

import com.challenge.challenge.domain.ZoneTrips;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.challenge.challenge.domain.TopZonesSort.DROPOFFS;
import static com.challenge.challenge.domain.TopZonesSort.PICKUPS;
import static org.assertj.core.api.Assertions.assertThat;

class TripsDAOTest extends BaseTest {

    @Autowired
    protected TripsDAO dao;

    @Test
    void testTopZonesSortedByPickup() {
        var expected = List.of(
            new ZoneTrips("Astoria", 27L, 29L),
            new ZoneTrips("Newark Airport", 12L, 11L),
            new ZoneTrips("Alphabet City", 6L, 4L),
            new ZoneTrips("Baisley Park", 6L, 4L),
            new ZoneTrips("Arrochar/Fort Wadsworth", 2L, 7L)
        );
        List<ZoneTrips> topZones = dao.getTopZones(PICKUPS);
        assertThat(topZones).isEqualTo(expected);
    }

    @Test
    void testTopZonesSortedByDropOff() {
        var expected = List.of(
            new ZoneTrips("Astoria", 27L, 29L),
            new ZoneTrips("Newark Airport", 12L, 11L),
            new ZoneTrips("Arrochar/Fort Wadsworth", 2L, 7L),
            new ZoneTrips("Alphabet City", 6L, 4L),
            new ZoneTrips("Baisley Park", 6L, 4L)
        );
        List<ZoneTrips> topZones = dao.getTopZones(DROPOFFS);
        assertThat(topZones).isEqualTo(expected);
    }
}