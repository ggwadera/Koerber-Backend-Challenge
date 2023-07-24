package com.challenge.challenge.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record TopZonesRequest(

    @NotNull
    @Pattern(
        regexp = "(pickups)|(dropoffs)",
        flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "sort should be either 'pickups' or 'dropoffs'"
    )
    String sort
) {
}
