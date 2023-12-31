package com.challenge.challenge.domain;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public record TripFilter(
    Long pickupLocationId,
    Long dropoffLocationId,
    LocalDate pickupDateTime,
    LocalDate pickupDateTimeStart,
    LocalDate pickupDateTimeEnd,
    LocalDate dropoffDateTime,
    LocalDate dropoffDateTimeStart,
    LocalDate dropoffDateTimeEnd
) {

    private Specification<Trip> locationIdEqualTo(String column, Long id) {
        return (root, query, cb) -> cb.equal(root.join(column + "Location").get("id"), id);
    }

    private Specification<Trip> dateLessThanOrEqualTo(String column, LocalDate time) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(column), time.atStartOfDay());
    }

    private Specification<Trip> dateGreaterThanOrEqualTo(String column, LocalDate time) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(column + "DateTime"), time.atStartOfDay());
    }

    private Specification<Trip> dateEqualTo(String column, LocalDate time) {
        return (root, query, cb) -> cb.between(root.get(column + "DateTime"), time.atStartOfDay(), time.plusDays(1).atStartOfDay());
    }

    public Specification<Trip> toSpecification() {
        Specification<Trip> spec = Specification.where(null);
        if (pickupLocationId != null) spec = spec.and(locationIdEqualTo("pickup", pickupLocationId));
        if (dropoffLocationId != null) spec = spec.and(locationIdEqualTo("dropoff", pickupLocationId));
        if (pickupDateTime != null) spec = spec.and(dateEqualTo("pickup", pickupDateTime));
        if (pickupDateTimeStart != null) spec = spec.and(dateGreaterThanOrEqualTo("pickup", pickupDateTimeStart));
        if (pickupDateTimeEnd != null) spec = spec.and(dateLessThanOrEqualTo("pickup", pickupDateTimeStart));
        if (dropoffDateTime != null) spec = spec.and(dateEqualTo("dropoff", dropoffDateTime));
        if (dropoffDateTimeStart != null) spec = spec.and(dateGreaterThanOrEqualTo("dropoff", dropoffDateTimeStart));
        if (dropoffDateTimeEnd != null) spec = spec.and(dateLessThanOrEqualTo("dropoff", dropoffDateTimeStart));
        return spec;
    }
}
