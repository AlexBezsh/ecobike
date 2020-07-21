package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.ElectricBike;
import com.qualityunit.ecobike.repository.BikeRepository;
import com.qualityunit.ecobike.view.ConsoleView;

public class AddNewElectricBikeCommand implements Command {

    private String brand;
    private int maxSpeed;
    private int weight;
    private boolean lights;
    private int batteryCapacity;
    private String color;
    private int price;

    @Override
    public void execute() {

        brand = ConsoleView.readString("Enter the brand name of a new e-bike, please.");
        maxSpeed = ConsoleView.readNumberBetween("Enter its max speed in km/h.", 1, 500);
        weight = ConsoleView.readNumberBetween("Enter the weight of a new bike in grams.", 1, 1_000_000);
        lights = ConsoleView.readBoolean("Does it have lights at front and back?");
        batteryCapacity = ConsoleView.readNumberBetween("Enter its battery capacity in mAh.", 1, 1_000_000);
        color = ConsoleView.readString("Enter the color of a new bike, please.");
        price = ConsoleView.readNumberBetween("Enter the price in euros.", 0, 1_000_000);

        correctMistakes();

        ElectricBike bike = new ElectricBike(brand, maxSpeed, weight, lights, batteryCapacity, color, price);
        BikeRepository.getInstance().saveNewBike(bike);

        ConsoleView.write("New bike has been saved.");
    }

    private void correctMistakes() {
        boolean needsCorrection = ConsoleView.readBoolean("New bike characteristics: " +
                "\nBrand - " + brand +
                "\nMax speed (km/h) - " + maxSpeed +
                "\nWeight (in grams) - " + weight +
                "\nHas lights - " + lights +
                "\nBattery capacity in mAh - " + batteryCapacity +
                "\nColor - " + color +
                "\nPrice - " + price +
                "\nDo you want to change something?");

        if (needsCorrection) {
            correctOneField();
            correctMistakes();
        }
    }

    private void correctOneField() {
        int fieldNumber = ConsoleView.readNumberBetween("Which characteristic do you want to correct?" +
                "\n1 - brand" +
                "\n2 - max speed" +
                "\n3 - weight" +
                "\n4 - lights availability" +
                "\n5 - battery capacity" +
                "\n6 - color" +
                "\n7 - price", 1, 7);

        switch (fieldNumber) {
            case 1:
                brand = ConsoleView.readString("Enter the brand name of a new e-bike, please.");
                break;
            case 2:
                maxSpeed = ConsoleView.readNumberBetween("Enter max speed of a new e-bike in km/h.", 1, 500);
                break;
            case 3:
                weight = ConsoleView.readNumberBetween("Enter the weight of a new bike in grams.", 1, 1_000_000);
                break;
            case 4:
                lights = ConsoleView.readBoolean("Does a new bike have lights at front and back?");
                break;
            case 5:
                batteryCapacity = ConsoleView.readNumberBetween("Enter battery capacity of a new bike in mAh.", 1, 1_000_000);
                break;
            case 6:
                color = ConsoleView.readString("Enter the color of a new bike, please.");
                break;
            case 7:
                price = ConsoleView.readNumberBetween("Enter the price in euros.", 0, 1_000_000);
                break;
        }
    }
}
