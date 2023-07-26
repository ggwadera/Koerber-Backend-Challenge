package com.challenge.challenge.domain;

public enum ZoneTotalsSort {
    PICKUPS("pickupTotal"), DROPOFFS("dropoffTotal");

    private final String column;

    ZoneTotalsSort(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }

    public static ZoneTotalsSort of(String sort) {
        return switch (sort.toUpperCase()) {
            case "PICKUPS" -> PICKUPS;
            case "DROPOFFS" -> DROPOFFS;
            default -> null;
        };
    }
}
