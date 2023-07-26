package com.challenge.challenge.response;

import com.challenge.challenge.domain.Location;

public record LocationResponse(
    Long id,
    String borough,
    String zone,
    String serviceZone
) {

    public LocationResponse(Location location) {
        this(location.getId(), location.getBorough(), location.getZone(), location.getServiceZone());
    }
}
