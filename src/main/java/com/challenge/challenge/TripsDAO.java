package com.challenge.challenge;

import com.challenge.challenge.domain.ZoneDailyTrips;
import com.challenge.challenge.domain.ZoneTotals;
import com.challenge.challenge.domain.ZoneTotalsSort;
import com.challenge.challenge.repository.ZoneDailyTripsRepository;
import com.challenge.challenge.repository.ZoneTotalsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class TripsDAO {

    private final ZoneTotalsRepository zoneTotalsRepository;
    private final ZoneDailyTripsRepository zoneDailyTripsRepository;

    public TripsDAO(ZoneTotalsRepository zoneTotalsRepository, ZoneDailyTripsRepository zoneDailyTripsRepository) {
        this.zoneTotalsRepository = zoneTotalsRepository;
        this.zoneDailyTripsRepository = zoneDailyTripsRepository;
    }

    public List<ZoneTotals> getTopZones(final ZoneTotalsSort sort) {
        return zoneTotalsRepository.findTop5By(Sort.by(sort.getColumn()).descending());
    }

    public Optional<ZoneDailyTrips> getZoneTrips(final Long zoneId, final LocalDate date) {
        return zoneDailyTripsRepository.findByLocationIdAndDate(zoneId, date);
    }

}
