package com.challenge.challenge.response;

import com.challenge.challenge.domain.Trip;

import java.time.LocalDateTime;

public record TripResponse(
    LocalDateTime pickupDateTime,
    LocalDateTime dropoffDateTime,
    LocationResponse pickupLocation,
    LocationResponse dropoffLocation
) {
    public TripResponse(Trip trip) {
        this(
            trip.getPickupDateTime(),
            trip.getDropoffDateTime(),
            new LocationResponse(trip.getPickupLocation()),
            new LocationResponse(trip.getDropoffLocation())
        );
    }
}
