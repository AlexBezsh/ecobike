package com.qualityunit.ecobike.model;

public enum  BikeType {

    FOLDING_BIKE ("FOLDING BIKE"),
    SPEEDELEC ("SPEEDELEC"),
    ELECTRIC_BIKE("E-BIKE");

    private String type;

    BikeType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
