package com.challenge.challenge.response;

import com.challenge.challenge.domain.ZoneTotals;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TopZonesResponse(
    @JsonProperty("top_zones")
    List<TopZone> topZones
) {

    public record TopZone(
        @JsonProperty("zone")
        String zone,
        @JsonProperty("pu_total")
        Long pickupTotal,
        @JsonProperty("do_total")
        Long dropoffTotal
    ) {

        public TopZone(ZoneTotals zoneTotals) {
            this(zoneTotals.getZone(), zoneTotals.getPickupTotal(), zoneTotals.getDropoffTotal());
        }
    }


}
