package com.challenge.challenge;

import com.challenge.challenge.domain.*;
import com.challenge.challenge.repository.TripRepository;
import com.challenge.challenge.repository.ZoneDailyTripsRepository;
import com.challenge.challenge.repository.ZoneTotalsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class TripsDAO {

    private final ZoneTotalsRepository zoneTotalsRepository;
    private final ZoneDailyTripsRepository zoneDailyTripsRepository;
    private final TripRepository tripRepository;

    public TripsDAO(ZoneTotalsRepository zoneTotalsRepository, ZoneDailyTripsRepository zoneDailyTripsRepository, TripRepository tripRepository) {
        this.zoneTotalsRepository = zoneTotalsRepository;
        this.zoneDailyTripsRepository = zoneDailyTripsRepository;
        this.tripRepository = tripRepository;
    }

    public List<ZoneTotals> getTopZones(final ZoneTotalsSort sort) {
        return zoneTotalsRepository.findTop5By(Sort.by(sort.getColumn()).descending());
    }

    public Optional<ZoneDailyTrips> getZoneTrips(final Long zoneId, final LocalDate date) {
        return zoneDailyTripsRepository.findByLocationIdAndDate(zoneId, date);
    }

    public Page<Trip> findTrips(TripFilter filter, Pageable pageable) {
        return tripRepository.findAll(filter.toSpecification(), pageable);
    }

}
