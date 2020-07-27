package com.qualityunit.ecobike.model;

import java.util.Objects;

public class ElectricBike extends AbstractBike {

    private int maxSpeed;
    private int batteryCapacity;

    public ElectricBike() {
    }

    public ElectricBike(Long id, String brand, int maxSpeed, int weight, boolean lights, int batteryCapacity, String color, int price) {
        super(id, brand, weight, lights, color, price, BikeType.ELECTRIC_BIKE);
        this.maxSpeed = maxSpeed;
        this.batteryCapacity = batteryCapacity;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public String toString() {
        String format = "%s %s with %d mAh battery and%s head/tail light.\nPrice: %d euros.";
        return String.format(format,
                type.toString(),
                brand,
                batteryCapacity,
                lights ? "" : " no",
                price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ElectricBike)) return false;
        ElectricBike electricBike = (ElectricBike) o;
        return id.equals(electricBike.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
