package com.challenge.challenge.domain;

public enum TopZonesSort {
    PICKUPS("pickup_total"), DROPOFFS("dropoff_total");

    private final String column;

    TopZonesSort(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }
}
