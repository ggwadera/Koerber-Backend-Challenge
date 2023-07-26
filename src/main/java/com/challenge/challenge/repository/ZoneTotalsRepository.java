package com.challenge.challenge.repository;

import com.challenge.challenge.domain.ZoneTotals;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZoneTotalsRepository extends JpaRepository<ZoneTotals, String> {

    List<ZoneTotals> findTop5By(Sort sort);
}