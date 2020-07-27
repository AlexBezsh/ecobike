package com.qualityunit.ecobike.controller;

import com.qualityunit.ecobike.Main;
import com.qualityunit.ecobike.controller.command.*;
import com.qualityunit.ecobike.model.AbstractBike;
import com.qualityunit.ecobike.model.BikeType;
import com.qualityunit.ecobike.repository.BikeRepository;
import com.qualityunit.ecobike.util.AppUtil;
import com.qualityunit.ecobike.view.ConsoleView;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BikeController {

    private static final Map<CommandType, Command> ALL_COMMANDS = new HashMap<>();
    public static String filePath;

    static {
        ALL_COMMANDS.put(CommandType.SHOW_CATALOG, new ShowCatalogCommand());
        ALL_COMMANDS.put(CommandType.ADD_NEW_FOLDING_BIKE, new AddNewFoldingBikeCommand());
        ALL_COMMANDS.put(CommandType.ADD_NEW_SPEEDELEC, new AddNewSpeedelecCommand());
        ALL_COMMANDS.put(CommandType.ADD_NEW_ELECTRIC_BIKE, new AddNewElectricBikeCommand());
        ALL_COMMANDS.put(CommandType.FIND_BIKES, new SearchBikesCommand());
        ALL_COMMANDS.put(CommandType.SAVE_DATA_TO_FILE, new SaveDataToFileCommand());
        ALL_COMMANDS.put(CommandType.EXIT, new ExitCommand());
    }

    private BikeController() {
    }

    public static void start(String filePath) {
        List<AbstractBike> bikes;
        if (filePath == null) {
            String s = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            if (s.contains("target")) {
                String pathToFile = s.substring(0, s.lastIndexOf("target")) + "ecobike.txt";
                start(pathToFile);
            } else {
                start("ecobike.txt");
            }
        } else if ((bikes = retrieveDataFromFile(filePath)) != null) {
            BikeController.filePath = filePath;
            BikeRepository.getInstance().initDatabase(bikes);
            ConsoleView.write("All data has been loaded from file: " + filePath);
        } else {
            String pathToFile = ConsoleView.readString("File: " + filePath + " not found or contains incorrect data. " +
                    "\nEnter an absolute path to file with correct data or 'exit' to finish the program");
            start(pathToFile);
        }
    }

    private static List<AbstractBike> retrieveDataFromFile(String filePath) {
        List<AbstractBike> bikes = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String s;
            while ((s = reader.readLine()) != null) {
                if (s.startsWith(BikeType.FOLDING_BIKE.toString())) {
                    bikes.add(AppUtil.getFoldingBikeFromString(s));
                } else if (s.startsWith(BikeType.ELECTRIC_BIKE.toString())) {
                    bikes.add(AppUtil.getElectricBikeFromString(s));
                } else if (s.startsWith(BikeType.SPEEDELEC.toString())) {
                    bikes.add(AppUtil.getSpeedelecFromString(s));
                } else {
                    return null;
                }
            }
            reader.close();
        } catch (Exception e) { // a lot of different exceptions may occur
            return null;
        }
        return bikes;
    }

    public static void execute(CommandType command) {
        if (command == CommandType.EXIT && BikeRepository.hasChanged()) {
            ALL_COMMANDS.get(CommandType.SAVE_DATA_TO_FILE).execute();
        }
        ALL_COMMANDS.get(command).execute();

    }

    public static CommandType getCommandType(int ordinal) {
        switch (ordinal) {
            case 1:
                return CommandType.SHOW_CATALOG;
            case 2:
                return CommandType.ADD_NEW_FOLDING_BIKE;
            case 3:
                return CommandType.ADD_NEW_SPEEDELEC;
            case 4:
                return CommandType.ADD_NEW_ELECTRIC_BIKE;
            case 5:
                return CommandType.FIND_BIKES;
            case 6:
                return CommandType.SAVE_DATA_TO_FILE;
            case 7:
                return CommandType.EXIT;
            default:
                return null;
        }
    }

    public static Command getCommand(CommandType type) {
        return ALL_COMMANDS.get(type);
    }
}
