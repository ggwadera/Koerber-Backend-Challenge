package com.challenge.challenge.response;

import com.challenge.challenge.domain.ZoneDailyTrips;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record ZoneTripsResponse(
    @JsonProperty("zone")
    String zone,
    @JsonProperty("date")
    LocalDate date,
    Long pickups,
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
