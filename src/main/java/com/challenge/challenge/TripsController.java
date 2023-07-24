package com.challenge.challenge;

import com.challenge.challenge.domain.TopZonesSort;
import com.challenge.challenge.domain.ZoneTrips;
import com.challenge.challenge.request.TopZonesRequest;
import com.challenge.challenge.response.TopZonesResponse;
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
    public TopZonesResponse getTopZones(@Valid TopZonesRequest request) {
        TopZonesSort zonesSort = TopZonesSort.valueOf(request.sort().toUpperCase());
        List<ZoneTrips> topZones = tripsDAO.getTopZones(zonesSort);
        return new TopZonesResponse(topZones.stream().map(TopZonesResponse.TopZone::new).toList());
    }
}
