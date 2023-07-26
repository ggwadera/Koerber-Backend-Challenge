package com.challenge.challenge.domain;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "trips_by_zone")
public class ZoneTotals {

    @Id
    private String zone;

    @Column(name = "pickup_total")
    private Long pickupTotal;

    @Column(name = "dropoff_total")
    private Long dropoffTotal;

    public ZoneTotals() {
    }

    public ZoneTotals(String zone, Long pickupTotal, Long dropoffTotal) {
        this.zone = zone;
        this.pickupTotal = pickupTotal;
        this.dropoffTotal = dropoffTotal;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Long getPickupTotal() {
        return pickupTotal;
    }

    public void setPickupTotal(Long pickupTotal) {
        this.pickupTotal = pickupTotal;
    }

    public Long getDropoffTotal() {
        return dropoffTotal;
    }

    public void setDropoffTotal(Long dropoffTotal) {
        this.dropoffTotal = dropoffTotal;
    }
}
