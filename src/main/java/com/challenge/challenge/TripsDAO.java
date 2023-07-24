package com.challenge.challenge;

import com.challenge.challenge.domain.TopZonesSort;
import com.challenge.challenge.domain.ZoneTrips;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TripsDAO {

    private final JdbcTemplate jdbcTemplate;

    public TripsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
