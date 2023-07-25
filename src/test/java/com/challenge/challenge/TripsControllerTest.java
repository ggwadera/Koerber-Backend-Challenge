package com.challenge.challenge;

import com.challenge.challenge.domain.ZoneTrips;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.challenge.challenge.domain.TopZonesSort.DROPOFFS;
import static com.challenge.challenge.domain.TopZonesSort.PICKUPS;
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
            new ZoneTrips("Astoria", 27L, 29L),
            new ZoneTrips("Newark Airport", 12L, 11L),
            new ZoneTrips("Alphabet City", 6L, 4L),
            new ZoneTrips("Baisley Park", 6L, 4L),
            new ZoneTrips("Arrochar/Fort Wadsworth", 2L, 7L)
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
            new ZoneTrips("Astoria", 27L, 29L),
            new ZoneTrips("Newark Airport", 12L, 11L),
            new ZoneTrips("Arrochar/Fort Wadsworth", 2L, 7L),
            new ZoneTrips("Alphabet City", 6L, 4L),
            new ZoneTrips("Baisley Park", 6L, 4L)
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
}