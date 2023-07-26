package com.challenge.challenge.request;

import com.challenge.challenge.domain.TripFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record TripFilterRequest(
    @Schema(description = "Filter by pickup location ID")
    Long pickupLocationId,
    @Schema(description = "Filter by drop-off location ID")
    Long dropoffLocationId,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Filter by pickup date")
    LocalDate pickupDateTime,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Filter by pickup date greater than this")
    LocalDate pickupDateTimeStart,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Filter by pickup date less than this")
    LocalDate pickupDateTimeEnd,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Filter by drop-off date")
    LocalDate dropoffDateTime,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Filter by drop-off date greater than this")
    LocalDate dropoffDateTimeStart,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Filter by drop-off date less than this")
    LocalDate dropoffDateTimeEnd
) {

    public TripFilter toDomain() {
        return new TripFilter(
            pickupLocationId(),
            dropoffLocationId(),
            pickupDateTime(),
            pickupDateTimeStart(),
            pickupDateTimeEnd(),
            dropoffDateTime(),
            dropoffDateTimeStart(),
            dropoffDateTimeEnd()
        );
    }
}
