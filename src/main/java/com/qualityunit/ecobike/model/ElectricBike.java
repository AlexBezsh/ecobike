package com.qualityunit.ecobike.model;

import java.util.Objects;

public class ElectricBike extends Bike {

    private int maxSpeed;
    private int batteryCapacity;

    public ElectricBike() {
    }

    public ElectricBike(String brand, int maxSpeed, int weight, boolean lights, int batteryCapacity, String color, int price) {
        super(brand, weight, lights, color, price, BikeType.ELECTRIC_BIKE);
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
        return brand.equals(electricBike.brand)
                && maxSpeed == electricBike.maxSpeed
                && weight == electricBike.weight
                && lights == electricBike.lights
                && batteryCapacity == electricBike.batteryCapacity
                && color.equals(electricBike.color)
                && price == electricBike.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, maxSpeed, weight, lights, batteryCapacity, color, price);
    }
}
