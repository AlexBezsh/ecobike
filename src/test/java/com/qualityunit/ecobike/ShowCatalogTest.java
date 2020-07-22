package com.qualityunit.ecobike;

import com.qualityunit.ecobike.controller.BikeController;
import com.qualityunit.ecobike.controller.command.CommandType;
import com.qualityunit.ecobike.exception.InterruptOperationException;
import com.qualityunit.ecobike.repository.BikeRepository;
import com.qualityunit.ecobike.view.ConsoleView;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class ShowCatalogTest {

    @Test
    public void showCatalog() {              //checks last page
        BikeController.start(null);
        int startQuantity = BikeRepository.getInstance().findAll().size();
        int pages = startQuantity % 10 == 0 ? startQuantity / 10 : startQuantity / 10 + 1;

        String s = "1" +               //10 items on one page
                "\n" + pages +         //show last page
                "\n" + (pages + 1) +   //show next - invalid number
                "\nexit";              //InterruptOperationException will be thrown

        ByteArrayOutputStream output = TestUtil.setInputAndGetOutput(s);

        try {
            BikeController.execute(CommandType.SHOW_CATALOG);
        } catch (InterruptOperationException ignore) { }

        Assert.assertTrue(output.toString().contains("Current page: 1. Number of pages: " + pages));
        Assert.assertTrue(output.toString().contains("Current page: " + pages + ". Number of pages: " + pages));
        Assert.assertTrue(output.toString().contains("FOLDING BIKE")
                || output.toString().contains("SPEEDELEC")
                || output.toString().contains("E-BIKE"));
        Assert.assertTrue(output.toString().contains("Invalid number. Please try again."));
    }
}