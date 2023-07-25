package com.challenge.challenge;

import org.junit.jupiter.api.Test;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TripsControllerTest extends BaseTest {

    @Test
    void getTopZonesSortedByPickup() throws Exception {
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
        mvc.perform(get("/top-zones").param("sort", "dropoffs"))
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
    void getTopZonesWrongParameter() throws Exception {
        mvc.perform(get("/top-zones").param("sort", "wrong"))
            .andExpect(status().isBadRequest());
    }
}