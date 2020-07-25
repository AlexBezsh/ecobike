package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.model.FoldingBike;
import com.qualityunit.ecobike.repository.BikeRepository;
import com.qualityunit.ecobike.util.AppUtil;
import com.qualityunit.ecobike.view.ConsoleView;

public class AddNewFoldingBikeCommand implements Command {

    private String brand;
    private int wheelsSize;
    private int gears;
    private int weight;
    private boolean lights;
    private String color;
    private int price;

    @Override
    public void execute() {
        brand = ConsoleView.readString("Enter the brand name of a new folding bike, please.");
        wheelsSize = ConsoleView.readNumberBetween("Enter the size of wheels in inches.", 1, 200);
        gears = ConsoleView.readNumberBetween("Enter the number of gears.", 1, 100);
        weight = ConsoleView.readNumberBetween("Enter the weight of a new bike in grams.", 1, 1_000_000);
        lights = ConsoleView.readBoolean("Does it have lights at front and back?");
        color = ConsoleView.readString("Enter the color of a new bike, please.");
        price = ConsoleView.readNumberBetween("Enter the price in euros.", 0, 1_000_000);

        correctMistakes();

        FoldingBike bike = new FoldingBike(
                AppUtil.idGenerator.getAndIncrement(), brand, wheelsSize, gears, weight, lights, color, price);
        BikeRepository.getInstance().saveNewBike(bike);

        ConsoleView.write("New bike has been saved.");
    }

    private void correctMistakes() {
        boolean needsCorrection = ConsoleView.readBoolean("New bike characteristics: " +
                "\nBrand - " + brand +
                "\nWheels size - " + wheelsSize +
                "\nNumber of gears - " + gears +
                "\nWeight (in grams) - " + weight +
                "\nHas lights - " + lights +
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
                "\n2 - wheels size" +
                "\n3 - number of gears" +
                "\n4 - weight" +
                "\n5 - lights availability" +
                "\n6 - color" +
                "\n7 - price", 1, 7);

        switch (fieldNumber) {
            case 1:
                brand = ConsoleView.readString("Enter the brand name of a new folding bike, please.");
                break;
            case 2:
                wheelsSize = ConsoleView.readNumberBetween("Enter the size of wheels in inches.", 1, 200);
                break;
            case 3:
                gears = ConsoleView.readNumberBetween("Enter the number of gears.", 1, 100);
                break;
            case 4:
                weight = ConsoleView.readNumberBetween("Enter the weight of a new bike in grams.", 1, 1_000_000);
                break;
            case 5:
                lights = ConsoleView.readBoolean("Does a new bike have lights at front and back?");
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
