package com.challenge.challenge.response;

import com.challenge.challenge.domain.ZoneTotals;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record TopZonesResponse(
    @JsonProperty("top_zones")
    List<TopZone> topZones
) {

    public record TopZone(
        @JsonProperty("zone")
        @Schema(example = "Midtown East")
        String zone,
        @JsonProperty("pu_total")
        @Schema(example = "435")
        Long pickupTotal,
        @JsonProperty("do_total")
        @Schema(example = "321")
        Long dropoffTotal
    ) {

        public TopZone(ZoneTotals zoneTotals) {
            this(zoneTotals.getZone(), zoneTotals.getPickupTotal(), zoneTotals.getDropoffTotal());
        }
    }


}
