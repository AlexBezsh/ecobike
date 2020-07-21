package com.qualityunit.ecobike.view;

import com.qualityunit.ecobike.controller.BikeController;
import com.qualityunit.ecobike.controller.command.CommandType;
import com.qualityunit.ecobike.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleView {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void write(String message) {
        System.out.println(message);
    }

    public static String readString(String message) {
        String s;
        while (true) {
            write(message);
            try {
                s = reader.readLine();
                if (s == null || s.trim().length() == 0) {
                    write("Empty string. Please try again.");
                } else if (s.equalsIgnoreCase("exit")) {
                    throw new InterruptOperationException();
                } else {
                    break;
                }
            } catch (IOException ignored) {
            }
        }
        return s;
    }

    public static int readNumberBetween(String message, int from, int to) { // to - inclusive
        int number;
        while (true) {
            try {
                number = Integer.parseInt(readString(message));
                if (number < from || number > to) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                write("Invalid number. Please try again.");
            }
        }
        return number;
    }

    public static int[] readRange(String message, int from, int to) {
        int[] range = new int[2];
        String s = readString(message + "\nEnter 'any' to skip this characteristic or any other symbol to fill the form");
        if (s.equals("any")) {
            range[0] = from;
            range[1] = to;
            return range;
        }
        int start = readNumberBetween(message + "\nFrom:", from, to);
        int end = readNumberBetween(message + "\nTo:", start, to);
        range[0] = start;
        range[1] = end;
        return range;
    }

    public static boolean readBoolean(String message) {
        message = message + "\n1 - Yes, 2 - No";
        int choice = readNumberBetween(message, 1, 2);
        return choice == 1;
    }

    public static CommandType askCommand() {
        String message = "Choose one of the options, please"  +
                "\n\t1 - Show the entire EcoBike catalog" +
                "\n\t2 - Add a new folding bike" +
                "\n\t3 - Add a new speedelec" +
                "\n\t4 - Add a new e-bike" +
                "\n\t5 - Search bikes" +
                "\n\t6 - Write to file" +
                "\n\t7 - Exit" +
                "\nNote that you can return to this menu by entering 'exit' on any stage of using the program";
        int i = readNumberBetween(message, 1, 7);
        CommandType command = BikeController.getCommandType(i);
        if (command != null){
            return command;
        } else {
            write("No such operation found");
            return askCommand();
        }
    }

    public static void setReader(BufferedReader reader) {  //used for testing
        ConsoleView.reader = reader;
    }
}
