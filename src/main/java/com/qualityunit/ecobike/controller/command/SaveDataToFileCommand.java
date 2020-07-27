package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.Main;
import com.qualityunit.ecobike.controller.BikeController;
import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.repository.BikeRepository;
import com.qualityunit.ecobike.util.AppUtil;
import com.qualityunit.ecobike.view.ConsoleView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveDataToFileCommand implements Command {

    @Override
    public void execute() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(BikeController.filePath));
        } catch (IOException e) {
            writer = getWriter();
        }
        writeDataToFile(writer);

        ConsoleView.write("Data has been successfully saved.");

        BikeRepository.dataSaved();
    }

    private BufferedWriter getWriter() {
        BufferedWriter writer;
        do {
            int choice = ConsoleView.readNumberBetween("In which file do you want to save the information" +
                    "\n1 - Enter path to file" +
                    "\n2 - Generate file automatically", 1, 2);
            String filePath;
            try {
                if (choice == 1) {
                    filePath = ConsoleView.readString("Enter an absolute file path for saving data");
                } else {
                    String s = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                    filePath = s + "ecobike.txt";
                }
                writer = new BufferedWriter(new FileWriter(filePath));
                break;
            } catch (IOException e) {
                ConsoleView.write("An exception occurred. Please try again.");
            }
        } while (true);
        return writer;
    }

    private void writeDataToFile(BufferedWriter writer) {
        if (writer == null) {
            writer = getWriter();
        }
        synchronized (BikeRepository.getInstance()) {
            List<AbstractBike> bikes = BikeRepository.getInstance().findAll();
            int bikesQuantity = bikes.size();
            try {
                for (int i = 0; i < bikesQuantity; i++) {
                    writer.write(AppUtil.bikeToFileString(bikes.get(i)));
                    if (i != bikesQuantity - 1) {
                        writer.newLine();
                    }
                }
                writer.close();
            } catch (IOException e) {
                ConsoleView.write("An exception occurred during writing to a file. Try with another one.");
                writeDataToFile(null);
            }
        }
    }
}
