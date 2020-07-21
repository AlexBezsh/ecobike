package com.qualityunit.ecobike.model;

import java.util.Objects;

public class Speedelec extends Bike {

    private int maxSpeed;
    private int batteryCapacity;

    public Speedelec() {
    }

    public Speedelec(String brand, int maxSpeed, int weight, boolean lights, int batteryCapacity, String color, int price) {
        super(brand, weight, lights, color, price, BikeType.SPEEDELEC);
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
        if (!(o instanceof Speedelec)) return false;
        Speedelec speedelec = (Speedelec) o;
        return brand.equals(speedelec.brand)
                && maxSpeed == speedelec.maxSpeed
                && weight == speedelec.weight
                && lights == speedelec.lights
                && batteryCapacity == speedelec.batteryCapacity
                && color.equals(speedelec.color)
                && price == speedelec.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, maxSpeed, weight, lights, batteryCapacity, color, price);
    }
}
