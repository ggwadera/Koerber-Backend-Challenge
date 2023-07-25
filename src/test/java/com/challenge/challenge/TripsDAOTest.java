package com.challenge.challenge;

import com.challenge.challenge.domain.ZoneDateTrips;
import com.challenge.challenge.domain.ZoneTrips;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.challenge.challenge.domain.TopZonesSort.DROPOFFS;
import static com.challenge.challenge.domain.TopZonesSort.PICKUPS;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TripsDAOTest {

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

    @Test
    void testGetZoneTrips() {
        LocalDate date = LocalDate.of(2023, 1, 3);
        var expected = new ZoneDateTrips("Astoria", date, 2L, 2L);
        Optional<ZoneDateTrips> zoneTrips = dao.getZoneTrips(7L, date);
        assertThat(zoneTrips).isPresent().contains(expected);
    }

    @Test
    void testGetZoneTripsEmptyResult() {
        LocalDate date = LocalDate.of(2024, 1, 3);
        Optional<ZoneDateTrips> zoneTrips = dao.getZoneTrips(7L, date);
        assertThat(zoneTrips).isEmpty();
    }
}