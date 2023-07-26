package com.challenge.challenge;

import com.challenge.challenge.domain.Location;
import com.challenge.challenge.domain.Trip;
import com.challenge.challenge.domain.ZoneDailyTrips;
import com.challenge.challenge.domain.ZoneTotals;
import com.challenge.challenge.response.ZoneTripsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.challenge.challenge.domain.ZoneTotalsSort.DROPOFFS;
import static com.challenge.challenge.domain.ZoneTotalsSort.PICKUPS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TripsController.class)
class TripsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TripsDAO dao;

    @Test
    void getTopZonesSortedByPickup() throws Exception {
        when(dao.getTopZones(PICKUPS)).thenReturn(List.of(
            new ZoneTotals("Astoria", 27L, 29L),
            new ZoneTotals("Newark Airport", 12L, 11L),
            new ZoneTotals("Alphabet City", 6L, 4L),
            new ZoneTotals("Baisley Park", 6L, 4L),
            new ZoneTotals("Arrochar/Fort Wadsworth", 2L, 7L)
        ));
        mvc.perform(get("/top-zones").param("sort", "pickups"))
            .andExpectAll(
                status().isOk(),
                content().contentType(APPLICATION_JSON),
                jsonPath("$.top_zones").isArray(),
                jsonPath("$.top_zones.length()").value(5),
                jsonPath("$.top_zones[0].zone").value("Astoria"),
                jsonPath("$.top_zones[0].pu_total").value(27L),
                jsonPath("$.top_zones[0].do_total").value(29L)
            );
    }

    @Test
    void getTopZonesSortedByDropOff() throws Exception {
        when(dao.getTopZones(DROPOFFS)).thenReturn(List.of(
            new ZoneTotals("Astoria", 27L, 29L),
            new ZoneTotals("Newark Airport", 12L, 11L),
            new ZoneTotals("Arrochar/Fort Wadsworth", 2L, 7L),
            new ZoneTotals("Alphabet City", 6L, 4L),
            new ZoneTotals("Baisley Park", 6L, 4L)
        ));
        mvc.perform(get("/top-zones").param("sort", "dropoffs"))
            .andExpectAll(
                status().isOk(),
                content().contentType(APPLICATION_JSON),
                jsonPath("$.top_zones").isArray(),
                jsonPath("$.top_zones.length()").value(5),
                jsonPath("$.top_zones[2].zone").value("Arrochar/Fort Wadsworth"),
                jsonPath("$.top_zones[2].pu_total").value(2L),
                jsonPath("$.top_zones[2].do_total").value(7L)
            );
    }

    @Test
    void getTopZonesWrongParameter() throws Exception {
        mvc.perform(get("/top-zones").param("sort", "wrong"))
            .andExpect(status().isBadRequest());
        verify(dao, never()).getTopZones(any());
    }

    @Test
    void getZoneTrips() throws Exception {
        LocalDate date = LocalDate.parse("2023-01-03");
        ZoneDailyTrips zoneDateTrips = new ZoneDailyTrips(7, "Astoria", date, 2L, 2L);
        when(dao.getZoneTrips(7L, date)).thenReturn(Optional.of(zoneDateTrips));
        var expected = new ZoneTripsResponse(zoneDateTrips);
        mvc.perform(get("/zone-trips")
                .param("zone", "7")
                .param("date", date.toString()))
            .andExpectAll(
                status().isOk(),
                content().contentType(APPLICATION_JSON),
                content().json(mapper.writeValueAsString(expected))
            );
    }

    @Test
    void searchTrips() throws Exception {
        when(dao.findTrips(any(), any())).thenReturn(new PageImpl<>(List.of(getTrip(), getTrip(), getTrip())));
        mvc.perform(get("/list-yellow")
            .param("page", "0")
            .param("size", "10"))
            .andExpectAll(
                status().isOk(),
                content().contentType(APPLICATION_JSON),
                jsonPath("$.number").value(0),
                jsonPath("$.size").value(3),
                jsonPath("$.content.length()").value(3),
                jsonPath("$.last").value(true)
            );
    }

    private static Trip getTrip() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new Trip(
            random.nextLong(),
            LocalDateTime.now().minusMinutes(random.nextLong(60)),
            LocalDateTime.now(),
            new Location(
                random.nextLong(),
                "pickup",
                "pickup",
                "pickup"
            ),
            new Location(
                random.nextLong(),
                "dropoff",
                "dropoff",
                "dropoff"
            )
        );
    }
}