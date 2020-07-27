package com.qualityunit.ecobike.model;

import java.util.Objects;

public class FoldingBike extends AbstractBike {

    private int wheelsSize;
    private int gears;

    public FoldingBike() {
    }

    public FoldingBike(Long id, String brand, int wheelsSize, int gears, int weight, boolean lights, String color, int price) {
        super(id, brand, weight, lights, color, price, BikeType.FOLDING_BIKE);
        this.wheelsSize = wheelsSize;
        this.gears = gears;
    }

    public int getWheelsSize() {
        return wheelsSize;
    }

    public void setWheelsSize(int wheelsSize) {
        this.wheelsSize = wheelsSize;
    }

    public int getGears() {
        return gears;
    }

    public void setGears(int gears) {
        this.gears = gears;
    }

    @Override
    public String toString() {
        String format = "%s %s with %d gear(s) and%s head/tail light.\nPrice: %d euros.";
        return String.format(format,
                type.toString(),
                brand,
                gears,
                lights ? "" : " no",
                price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoldingBike)) return false;
        FoldingBike foldingBike = (FoldingBike) o;
        return id.equals(foldingBike.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
