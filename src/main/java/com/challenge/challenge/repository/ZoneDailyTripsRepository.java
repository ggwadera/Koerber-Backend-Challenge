package com.challenge.challenge.repository;

import com.challenge.challenge.domain.ZoneDailyTrips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface ZoneDailyTripsRepository extends JpaRepository<ZoneDailyTrips, ZoneDailyTrips.ZoneDailyTripsPK> {

    @Query("select z from ZoneDailyTrips z join fetch z.location where z.id.locationId = :locationId and z.id.date = :date")
    Optional<ZoneDailyTrips> findByLocationIdAndDate(Long locationId, LocalDate date);
}