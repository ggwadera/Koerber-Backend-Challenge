package com.challenge.challenge;

import com.challenge.challenge.domain.ZoneDailyTrips;
import com.challenge.challenge.domain.ZoneTotalsSort;
import com.challenge.challenge.request.TripFilterRequest;
import com.challenge.challenge.request.ZoneTripsRequest;
import com.challenge.challenge.response.TopZonesResponse;
import com.challenge.challenge.response.TripResponse;
import com.challenge.challenge.response.ZoneTripsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TripsController {

    private final TripsDAO tripsDAO;

    public TripsController(TripsDAO tripsDAO) {
        this.tripsDAO = tripsDAO;
    }

    @GetMapping(path = "/top-zones", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Top 5 zones", description = "Returns a list with the top 5 zones sorted by total pickups or drop-offs")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
        @ApiResponse(responseCode = "400", description = "Incorrect parameter value", content = @Content())
    })
    public ResponseEntity<TopZonesResponse> getTopZones(
        @RequestParam
        @Parameter(description = "Sort order", schema = @Schema(allowableValues = {"pickups", "dropoffs"}))
        String sort
    ) {
        ZoneTotalsSort zonesSort = ZoneTotalsSort.of(sort);
        if (zonesSort == null) return ResponseEntity.badRequest().build();
        var results = tripsDAO.getTopZones(zonesSort).stream()
            .map(TopZonesResponse.TopZone::new)
            .toList();
        return ResponseEntity.ok(new TopZonesResponse(results));
    }

    @GetMapping(path = "/zone-trips", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Trips by zone and date", description = "Return the sum of the pickups and drop-offs in just one zone and one date.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content())
    })
    public ResponseEntity<ZoneTripsResponse> getZoneTrips(@Valid @ParameterObject ZoneTripsRequest request) {
        Optional<ZoneDailyTrips> zoneTrips = tripsDAO.getZoneTrips(request.zone(), request.date());
        return zoneTrips
            .map(ZoneTripsResponse::new)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/list-yellow", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Trips search", description = "Returns data from the trips with pagination, filtering and sorting.")
    public Page<TripResponse> getTripsPage(
        @ParameterObject Pageable pageable,
        @ParameterObject TripFilterRequest filterRequest
    ) {
        return tripsDAO.findTrips(filterRequest.toDomain(), pageable)
            .map(TripResponse::new);
    }
}
