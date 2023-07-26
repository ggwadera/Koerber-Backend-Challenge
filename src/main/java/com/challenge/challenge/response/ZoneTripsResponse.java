package com.challenge.challenge.response;

import com.challenge.challenge.domain.ZoneDailyTrips;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record ZoneTripsResponse(
    @JsonProperty("zone")
    @Schema(example = "Bushwick North")
    String zone,

    @JsonProperty("date")
    @Schema(example = "2018-01-12")
    LocalDate date,

    @Schema(example = "3245")
    Long pickups,

    @Schema(example = "1345")
    Long dropoffs
) {

    public ZoneTripsResponse(ZoneDailyTrips zoneDailyTrips) {
        this(
            zoneDailyTrips.getLocation().getZone(),
            zoneDailyTrips.getDate(),
            zoneDailyTrips.getPickupTotal(),
            zoneDailyTrips.getDropoffTotal()
        );
    }
}
