package com.challenge.challenge;

import com.challenge.challenge.domain.ZoneDailyTrips;
import com.challenge.challenge.domain.ZoneTotals;
import com.challenge.challenge.domain.ZoneTotalsSort;
import com.challenge.challenge.repository.ZoneTotalsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TripsDAO {

    private final ZoneTotalsRepository zoneTotalsRepository;

    public TripsDAO(ZoneTotalsRepository zoneTotalsRepository, ZoneDailyTripsRepository zoneDailyTripsRepository) {
        this.zoneTotalsRepository = zoneTotalsRepository;
        this.zoneDailyTripsRepository = zoneDailyTripsRepository;
    }

    public List<ZoneTotals> getTopZones(final ZoneTotalsSort sort) {
        return zoneTotalsRepository.findTop5By(Sort.by(sort.getColumn()).descending());
    }

    public List<ZoneTrips> getTopZones(TopZonesSort sort) {
        return jdbcTemplate.query("select zone, pickup_total, dropoff_total from trips_by_zone " +
                                  "order by " + sort.getColumn() + " desc limit 5",
            (rs, rowNum) -> new ZoneTrips(
                rs.getString("zone"),
                rs.getLong("pickup_total"),
                rs.getLong("dropoff_total"))
        );
    }

}
