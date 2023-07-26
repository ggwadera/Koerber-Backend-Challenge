package com.challenge.challenge.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public record ZoneTripsRequest(
    @Positive
    @NotNull
    @Schema(description = "Zone ID")
    Long zone,

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Date in the format YYYY-MM-DD", example = "2023-01-01")
    LocalDate date
) {
}
