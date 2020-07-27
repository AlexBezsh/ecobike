package com.qualityunit.ecobike.util;

import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.ElectricBike;
import com.qualityunit.ecobike.model.FoldingBike;
import com.qualityunit.ecobike.model.Speedelec;

import java.util.concurrent.atomic.AtomicLong;

public class AppUtil {

    public static AtomicLong idGenerator = new AtomicLong(1);

    public static FoldingBike getFoldingBikeFromString(String s) {
        s = s.substring(13);
        String[] bikeCharacteristics = s.split("; ");
        return new FoldingBike(idGenerator.getAndIncrement(),
                bikeCharacteristics[0],
                Integer.parseInt(bikeCharacteristics[1]),
                Integer.parseInt(bikeCharacteristics[2]),
                Integer.parseInt(bikeCharacteristics[3]),
                bikeCharacteristics[4].equalsIgnoreCase("true"),
                bikeCharacteristics[5],
                Integer.parseInt(bikeCharacteristics[6]));
    }

    public static Speedelec getSpeedelecFromString(String s) {
        s = s.substring(10);
        String[] bikeCharacteristics = s.split("; ");
        return new Speedelec(idGenerator.getAndIncrement(),
                bikeCharacteristics[0],
                Integer.parseInt(bikeCharacteristics[1]),
                Integer.parseInt(bikeCharacteristics[2]),
                bikeCharacteristics[3].equalsIgnoreCase("true"),
                Integer.parseInt(bikeCharacteristics[4]),
                bikeCharacteristics[5],
                Integer.parseInt(bikeCharacteristics[6]));
    }

    public static ElectricBike getElectricBikeFromString(String s) {
        s = s.substring(7);
        String[] bikeCharacteristics = s.split("; ");
        return new ElectricBike(idGenerator.getAndIncrement(),
                bikeCharacteristics[0],
                Integer.parseInt(bikeCharacteristics[1]),
                Integer.parseInt(bikeCharacteristics[2]),
                bikeCharacteristics[3].equalsIgnoreCase("true"),
                Integer.parseInt(bikeCharacteristics[4]),
                bikeCharacteristics[5],
                Integer.parseInt(bikeCharacteristics[6]));
    }

    public static String bikeToFileString(AbstractBike bike) {
        if (bike instanceof FoldingBike) {
            return toFileString((FoldingBike) bike);
        } else if (bike instanceof Speedelec) {
            return toFileString((Speedelec) bike);
        } else if (bike instanceof ElectricBike) {
            return toFileString((ElectricBike) bike);
        } else {
            return null;
        }
    }

    public static String toFileString(ElectricBike bike) {
        return String.format("%s %s; %d; %d; %s; %d; %s; %d",
                bike.getType().toString(),
                bike.getBrand(),
                bike.getMaxSpeed(),
                bike.getWeight(),
                bike.hasLights(),
                bike.getBatteryCapacity(),
                bike.getColor(),
                bike.getPrice());
    }

    public static String toFileString(FoldingBike bike) {
        return String.format("%s %s; %d; %d; %d; %s; %s; %d",
                bike.getType().toString(),
                bike.getBrand(),
                bike.getWheelsSize(),
                bike.getGears(),
                bike.getWeight(),
                bike.hasLights(),
                bike.getColor(),
                bike.getPrice());
    }

    public static String toFileString(Speedelec bike) {
        return String.format("%s %s; %d; %d; %s; %d; %s; %d",
                bike.getType().toString(),
                bike.getBrand(),
                bike.getMaxSpeed(),
                bike.getWeight(),
                bike.hasLights(),
                bike.getBatteryCapacity(),
                bike.getColor(),
                bike.getPrice());
    }
}
