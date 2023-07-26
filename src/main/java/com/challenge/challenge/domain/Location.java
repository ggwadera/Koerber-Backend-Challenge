package com.challenge.challenge.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "borough")
    private String borough;


    @Column(name = "zone")
    private String zone;


    @Column(name = "service_zone")
    private String serviceZone;

    public Location() {
    }

    public Location(Long id, String borough, String zone, String serviceZone) {
        this.id = id;
        this.borough = borough;
        this.zone = zone;
        this.serviceZone = serviceZone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getServiceZone() {
        return serviceZone;
    }

    public void setServiceZone(String serviceZone) {
        this.serviceZone = serviceZone;
    }
}
