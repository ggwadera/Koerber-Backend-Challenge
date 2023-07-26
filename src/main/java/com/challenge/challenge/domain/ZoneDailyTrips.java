package com.challenge.challenge.domain;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Immutable
@Table(name = "trips_by_zone_and_date")
public class ZoneDailyTrips {

    @EmbeddedId
    private ZoneDailyTripsPK id;

    @Column(name = "pickup_total")
    private Long pickupTotal;

    @Column(name = "dropoff_total")
    private Long dropoffTotal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id", insertable = false, updatable = false)
    private Location location;

    public ZoneDailyTrips() {
    }

    public ZoneDailyTrips(long locationId, String zone, LocalDate date, long pickupTotal, long dropoffTotal) {
        this.id = new ZoneDailyTripsPK();
        this.id.locationId = locationId;
        this.id.date = date;
        this.location = new Location();
        this.location.setId(locationId);
        this.location.setZone(zone);
        this.pickupTotal = pickupTotal;
        this.dropoffTotal = dropoffTotal;
    }

    public ZoneDailyTripsPK getId() {
        return id;
    }

    public void setId(ZoneDailyTripsPK id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return id.getDate();
    }

    public void setDate(LocalDate date) {
        this.id.date = date;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Embeddable
    public static class ZoneDailyTripsPK implements Serializable {
        @Serial
        private static final long serialVersionUID = 1576993603682753842L;
        @Column(name = "location_id")
        private Long locationId;
        private LocalDate date;

        public Long getLocationId() {
            return locationId;
        }

        public void setLocationId(Long locationId) {
            this.locationId = locationId;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }

}
















