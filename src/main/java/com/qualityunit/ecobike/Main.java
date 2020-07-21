package com.qualityunit.ecobike;

import com.qualityunit.ecobike.controller.BikeController;
import com.qualityunit.ecobike.controller.command.CommandType;
import com.qualityunit.ecobike.exception.InterruptOperationException;
import com.qualityunit.ecobike.view.ConsoleView;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        boolean startSuccess = false;
        try {
            BikeController.start(args.length == 1 ? args[0] : null);
            startSuccess = true;
        } catch (InterruptOperationException e) {
            BikeController.execute(CommandType.EXIT);
        }

        if (startSuccess) {
            CommandType command = null;
            do {
                try {
                    command = ConsoleView.askCommand();
                    BikeController.execute(command);
                } catch (InterruptOperationException ignored) {
                    // when user types "exit" on any stage, the cycle continue to iterate
                }
            } while (command != CommandType.EXIT);
        }
    }
}
