package com.challenge.challenge.repository;

import com.challenge.challenge.domain.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TripRepository extends JpaRepository<Trip, Long>, JpaSpecificationExecutor<Trip> {

    @EntityGraph(attributePaths = {"pickupLocation", "dropoffLocation"}, type = EntityGraph.EntityGraphType.LOAD)
    Page<Trip> findAll(Specification<Trip> spec, Pageable pageable);

}