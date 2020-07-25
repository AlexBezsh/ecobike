package com.qualityunit.ecobike;

import com.qualityunit.ecobike.controller.BikeController;
import com.qualityunit.ecobike.controller.command.CommandType;
import com.qualityunit.ecobike.model.ElectricBike;
import com.qualityunit.ecobike.repository.BikeRepository;
import com.qualityunit.ecobike.util.AppUtil;
import com.qualityunit.ecobike.view.ConsoleView;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExitTest {

    private static List<String> fileContent = new ArrayList<>();

    @BeforeClass
    public static void readFile() {
        BikeController.start(null);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(BikeController.filePath));
            String s;
            while ((s = reader.readLine()) != null) {
                fileContent.add(s);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void rollback() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(BikeController.filePath));
            String s;
            for (int i = 0; i < fileContent.size(); i++) {
                writer.write(fileContent.get(i));
                if (i != fileContent.size() - 1) {
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void exit() {      //add new bike and then exit - data should be saved to file
        String s ="Brand" +     //brand name
                "\n44" +        //max speed
                "\n12000" +     //weight
                "\n2" +         //no lights
                "\n30000" +     //battery capacity
                "\nblack" +     //color
                "\n567" +       //price
                "\n2";          //change smth? - no

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        int startQuantity = BikeRepository.getInstance().findAll().size();
        BikeController.execute(CommandType.ADD_NEW_ELECTRIC_BIKE);

        Assert.assertEquals(startQuantity, BikeRepository.getInstance().findAll().size() - 1);
        Assert.assertTrue(output.toString().contains("New bike characteristics: " +
                "\nBrand - Brand" +
                "\nMax speed (km/h) - 44" +
                "\nWeight (in grams) - 12000" +
                "\nHas lights - false" +
                "\nBattery capacity in mAh - 30000" +
                "\nColor - black" +
                "\nPrice - 567"));
        Assert.assertTrue(output.toString().contains("New bike has been saved."));

        BikeController.execute(CommandType.EXIT);

        String newBike = AppUtil.toFileString(
                new ElectricBike(AppUtil.idGenerator.getAndIncrement(), "Brand", 44, 12000, false, 30000, "black", 567));
        BufferedReader reader;
        boolean found = false;
        try {
            reader = new BufferedReader(new FileReader(BikeController.filePath));
            String fileString;
            while ((fileString = reader.readLine()) != null) {
                if (fileString.equals(newBike)) {
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(found);
    }
}
