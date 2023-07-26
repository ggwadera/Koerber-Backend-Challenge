package com.challenge.challenge.request;

import com.challenge.challenge.domain.TripFilter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record TripFilterRequest(
    Long pickupLocationId,
    Long dropoffLocationId,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate pickupDateTimeStart,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate pickupDateTimeEnd,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dropoffDateTimeStart,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dropoffDateTimeEnd
) {

    public TripFilter toDomain() {
        return new TripFilter(
            pickupLocationId(),
            dropoffLocationId(),
            pickupDateTimeStart(),
            pickupDateTimeEnd(),
            dropoffDateTimeStart(),
            dropoffDateTimeEnd()
        );
    }
}
