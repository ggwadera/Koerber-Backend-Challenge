package com.challenge.challenge;

import com.challenge.challenge.domain.Trip;
import com.challenge.challenge.domain.TripFilter;
import com.challenge.challenge.domain.ZoneDailyTrips;
import com.challenge.challenge.domain.ZoneTotals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.challenge.challenge.domain.ZoneTotalsSort.DROPOFFS;
import static com.challenge.challenge.domain.ZoneTotalsSort.PICKUPS;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TripsDAOTest {

    @Autowired
    protected TripsDAO dao;

    @Test
    void testTopZonesSortedByPickup() {
        var expected = List.of(
            new ZoneTotals("Astoria", 27L, 29L),
            new ZoneTotals("Newark Airport", 12L, 11L),
            new ZoneTotals("Alphabet City", 6L, 4L),
            new ZoneTotals("Baisley Park", 6L, 4L),
            new ZoneTotals("Arrochar/Fort Wadsworth", 2L, 7L)
        );
        List<ZoneTotals> topZones = dao.getTopZones(PICKUPS);
        assertThat(topZones).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testTopZonesSortedByDropOff() {
        var expected = List.of(
            new ZoneTotals("Astoria", 27L, 29L),
            new ZoneTotals("Newark Airport", 12L, 11L),
            new ZoneTotals("Arrochar/Fort Wadsworth", 2L, 7L),
            new ZoneTotals("Alphabet City", 6L, 4L),
            new ZoneTotals("Baisley Park", 6L, 4L)
        );
        List<ZoneTotals> topZones = dao.getTopZones(DROPOFFS);
        assertThat(topZones).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testGetZoneTrips() {
        LocalDate date = LocalDate.of(2023, 1, 7);
        Optional<ZoneDailyTrips> zoneTrips = dao.getZoneTrips(7L, date);
        assertThat(zoneTrips).isPresent();
        ZoneDailyTrips zoneDailyTrips = zoneTrips.get();
        assertThat(zoneDailyTrips.getPickupTotal()).isEqualTo(2L);
        assertThat(zoneDailyTrips.getDropoffTotal()).isEqualTo(2L);
    }

    @Test
    void testGetZoneTripsEmptyResult() {
        LocalDate date = LocalDate.of(2024, 1, 3);
        Optional<ZoneDailyTrips> zoneTrips = dao.getZoneTrips(7L, date);
        assertThat(zoneTrips).isEmpty();
    }

    @Test
    void testFindTripsByPickupLocationId() {
        var filter = filterWithPickupLocationId(1);
        Page<Trip> trips = dao.findTrips(filter, PageRequest.of(0, 10));
        assertThat(trips.toList()).isNotEmpty()
            .extracting("pickupLocation.id").containsOnly(1L);
    }

    @Test
    void testFindTripsByPickupDate() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        var filter = filterWithPickupDate(date);
        Page<Trip> trips = dao.findTrips(filter, PageRequest.of(0, 10));
        assertThat(trips.toList()).isNotEmpty();
        assertThat(trips.map(Trip::getPickupDateTime).map(LocalDateTime::toLocalDate).toList()).containsOnly(date);
    }

    private static TripFilter filterWithPickupLocationId(long id) {
        return new TripFilter(id, null, null, null, null, null, null, null);
    }

    private static TripFilter filterWithPickupDate(LocalDate date) {
        return new TripFilter(null, null, date, null, null, null, null, null);
    }
}