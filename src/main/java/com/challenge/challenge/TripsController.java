package com.challenge.challenge;

import com.challenge.challenge.domain.ZoneTotalsSort;
import com.challenge.challenge.request.TopZonesRequest;
import com.challenge.challenge.response.TopZonesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TripsController {

    private final TripsDAO tripsDAO;

    public TripsController(TripsDAO tripsDAO) {
        this.tripsDAO = tripsDAO;
    }

    @GetMapping("/top-zones")
    public ResponseEntity<TopZonesResponse> getTopZones(@Valid TopZonesRequest request) {
        ZoneTotalsSort zonesSort = ZoneTotalsSort.valueOf(request.sort().toUpperCase());
        var results = tripsDAO.getTopZones(zonesSort).stream()
            .map(TopZonesResponse.TopZone::new)
            .toList();
        return ResponseEntity.ok(new TopZonesResponse(results));
    }
    }
}
