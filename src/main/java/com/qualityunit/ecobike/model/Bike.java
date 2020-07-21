package com.qualityunit.ecobike.model;

public abstract class Bike {

    protected String brand;
    protected int weight;
    protected boolean lights;
    protected String color;
    protected int price;
    protected BikeType type;

    public Bike() {
    }

    public Bike(String brand, int weight, boolean lights, String color, int price, BikeType type) {
        this.brand = brand;
        this.weight = weight;
        this.lights = lights;
        this.color = color;
        this.price = price;
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean hasLights() {
        return lights;
    }

    public void setLights(boolean lights) {
        this.lights = lights;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public BikeType getType() {
        return type;
    }

    public void setType(BikeType type) {
        this.type = type;
    }

}
