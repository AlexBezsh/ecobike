package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.controller.BikeController;
import com.qualityunit.ecobike.model.*;
import com.qualityunit.ecobike.repository.BikeRepository;
import com.qualityunit.ecobike.view.ConsoleView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchBikesCommand implements Command {

    // general characteristics
    private String brandSearchPart;
    private int weightFrom;
    private int weightTo;
    private boolean lightsImportance;
    private String colorSearchPart;
    private int priceFrom;
    private int priceTo;

    // speedelec and e-bike characteristics
    private int maxSpeedFrom;
    private int maxSpeedTo;
    private int batteryCapacityFrom;
    private int batteryCapacityTo;

    // folding bike characteristics
    private int wheelsSizeFrom;
    private int wheelsSizeTo;
    private int gearsFrom;
    private int gearsTo;

    @Override
    public void execute() {
        setDefaultCharacteristics();

        int choice = ConsoleView.readNumberBetween("Which type of bike do you want to find?" +
                "\n1 - Folding bike" +
                "\n2 - Speedelec" +
                "\n3 - Electric bike", 1, 3);

        readGeneralCharacteristics();

        List<AbstractBike> bikes = null;
        switch (choice) {
            case 1:
                bikes = findFoldingBikes();
                break;
            case 2:
                bikes = findSpeedelecs();
                break;
            case 3:
                bikes = findElectricBikes();
                break;
        }
        showResults(bikes);
    }

    private void setDefaultCharacteristics() {
        brandSearchPart = "";
        weightFrom = 1;
        weightTo = 1_000_000;
        colorSearchPart = "";
        priceFrom = 0;
        priceTo = 1_000_000;

        maxSpeedFrom = 1;
        maxSpeedTo = 500;
        batteryCapacityFrom = 1;
        batteryCapacityTo = 1_000_000;

        wheelsSizeFrom = 1;
        wheelsSizeTo = 200;
        gearsFrom = 1;
        gearsTo = 100;
    }

    private void readGeneralCharacteristics() {
        String s;
        s = ConsoleView.readString("Enter the bike brand or type 'any' if brand is not important");
        if (!s.equals("any")) {
            brandSearchPart = s;
        }

        int[] range;
        range = ConsoleView.readRange("Weight range of the bike (in grams)", weightFrom, weightTo);
        weightFrom = range[0];
        weightTo = range[1];

        s = ConsoleView.readString("Enter color of the bike or type 'any' if color is not important");
        if (!s.equals("any")) {
            colorSearchPart = s;
        }

        range = ConsoleView.readRange("Price range of the bike", priceFrom, priceTo);
        priceFrom = range[0];
        priceTo = range[1];

        lightsImportance = ConsoleView.readBoolean("Is lights availability important?");
    }

    private List<AbstractBike> findFoldingBikes() {
        readGearsQuantityRange();
        readWheelsSizeRange();
        return getResultList(BikeType.FOLDING_BIKE);
    }

    private List<AbstractBike> findSpeedelecs() {
        readBatteryCapacityRange();
        readMaxSpeedRange();
        return getResultList(BikeType.SPEEDELEC);
    }

    private List<AbstractBike> findElectricBikes() {
        readBatteryCapacityRange();
        readMaxSpeedRange();
        return getResultList(BikeType.ELECTRIC_BIKE);
    }

    private void readGearsQuantityRange() {
        int[] range;
        range = ConsoleView.readRange("The number of gears range", gearsFrom, gearsTo);
        gearsFrom = range[0];
        gearsTo = range[1];
    }

    private void readWheelsSizeRange() {
        int[] range;
        range = ConsoleView.readRange("Wheels size range (in inch)", wheelsSizeFrom, wheelsSizeTo);
        wheelsSizeFrom = range[0];
        wheelsSizeTo = range[1];
    }

    private void readBatteryCapacityRange() {
        int[] range;
        range = ConsoleView.readRange("The battery capacity range (in mAh)", batteryCapacityFrom, batteryCapacityTo);
        batteryCapacityFrom = range[0];
        batteryCapacityTo = range[1];
    }

    private void readMaxSpeedRange() {
        int[] range;
        range = ConsoleView.readRange("The maximum speed range (in km/h)", maxSpeedFrom, maxSpeedTo);
        maxSpeedFrom = range[0];
        maxSpeedTo = range[1];
    }

    private List<AbstractBike> getResultList(BikeType type) {
        ConsoleView.write("Searching...");
        synchronized (BikeRepository.getInstance()) {
            if (type == BikeType.FOLDING_BIKE) {
                return getGeneralCharacteristicsStream(type)
                        .map(b -> (FoldingBike) b)
                        .filter(b -> b.getGears() >= gearsFrom
                                && b.getGears() <= gearsTo
                                && b.getWheelsSize() >= wheelsSizeFrom
                                && b.getWheelsSize() <= wheelsSizeTo)
                        .collect(Collectors.toList());
            } else if (type == BikeType.SPEEDELEC) {
                return getGeneralCharacteristicsStream(type)
                        .map(b -> (Speedelec) b)
                        .filter(b -> b.getBatteryCapacity() >= batteryCapacityFrom
                                && b.getBatteryCapacity() <= batteryCapacityTo
                                && b.getMaxSpeed() >= maxSpeedFrom
                                && b.getMaxSpeed() <= maxSpeedTo)
                        .collect(Collectors.toList());
            } else {
                return getGeneralCharacteristicsStream(type)
                        .map(b -> (ElectricBike) b)
                        .filter(b -> b.getBatteryCapacity() >= batteryCapacityFrom
                                && b.getBatteryCapacity() <= batteryCapacityTo
                                && b.getMaxSpeed() >= maxSpeedFrom
                                && b.getMaxSpeed() <= maxSpeedTo)
                        .collect(Collectors.toList());
            }
        }
    }

    private Stream<AbstractBike> getGeneralCharacteristicsStream(BikeType type) {
        return BikeRepository.getInstance()
                .findAll()
                .stream()
                .filter(b -> b.getType() == type
                        && b.getBrand().contains(brandSearchPart)
                        && b.getWeight() >= weightFrom && b.getWeight() <= weightTo
                        && b.getColor().contains(colorSearchPart)
                        && b.getPrice() >= priceFrom && b.getPrice() <= priceTo
                        && (!lightsImportance || b.hasLights()));
    }

    private void showResults(List<AbstractBike> result) {
        if (result != null) {
            ShowCatalogCommand showCatalog = (ShowCatalogCommand) BikeController.getCommand(CommandType.SHOW_CATALOG);
            showCatalog.setBikes(result);
            BikeController.execute(CommandType.SHOW_CATALOG);
        }
    }
}