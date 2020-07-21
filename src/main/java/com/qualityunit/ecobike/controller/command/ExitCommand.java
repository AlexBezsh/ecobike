package com.qualityunit.ecobike.controller.command;

import com.qualityunit.ecobike.view.ConsoleView;

public class ExitCommand implements Command {

    @Override
    public void execute() {
        ConsoleView.write("Program terminated.");
    }
}
