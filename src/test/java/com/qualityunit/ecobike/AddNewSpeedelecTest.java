package com.qualityunit.ecobike;

import com.qualityunit.ecobike.controller.BikeController;
import com.qualityunit.ecobike.controller.command.CommandType;
import com.qualityunit.ecobike.repository.BikeRepository;
import com.qualityunit.ecobike.view.ConsoleView;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class AddNewSpeedelecTest {

    @Test
    public void addNewSpeedelec() {
        String s ="Brand" +     //brand name
                "\n44" +        //max speed
                "\n12000" +     //weight
                "\n2" +         //no lights
                "\n30000" +     //battery capacity
                "\nblack" +     //color
                "\n566" +       //price
                "\n2";          //change smth? - no

        InputStream in = new ByteArrayInputStream(s.getBytes());
        System.setIn(in);
        ConsoleView.setReader(new BufferedReader(new InputStreamReader(System.in)));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(output);
        System.setOut(out);

        BikeController.start(null);
        int startQuantity = BikeRepository.getInstance().findAll().size();
        BikeController.execute(CommandType.ADD_NEW_SPEEDELEC);

        Assert.assertEquals(startQuantity, BikeRepository.getInstance().findAll().size() - 1);
        Assert.assertTrue(output.toString().contains("New bike characteristics: " +
                "\nBrand - Brand" +
                "\nMax speed (km/h) - 44" +
                "\nWeight (in grams) - 12000" +
                "\nHas lights - false" +
                "\nBattery capacity in mAh - 30000" +
                "\nColor - black" +
                "\nPrice - 566"));
        Assert.assertTrue(output.toString().contains("New bike has been saved."));
    }

    @Test
    public void addNewSpeedelecWithChanges() {
        String s ="Brand" +     //brand name
                "\n44" +        //max speed
                "\n12000" +     //weight
                "\n2" +         //no lights
                "\n30000" +     //battery capacity
                "\nblack" +     //color
                "\n566" +       //price
                "\n1" +         //change smth? - yes
                "\n1" +         //brand name
                "\nNewBrand" +  //new brand name
                "\n1" +         //change smth? - yes
                "\n2" +         //max speed
                "\n45" +        //new max speed
                "\n1" +         //change smth? - yes
                "\n3" +         //weight
                "\n13000" +     //new weight
                "\n1" +         //change smth? - yes
                "\n4" +         //lights availability
                "\n1" +         //have lights
                "\n1" +         //change smth? - yes
                "\n5" +         //battery capacity
                "\n25000" +     //new battery capacity
                "\n1" +         //change smth? - yes
                "\n6" +         //color
                "\nblue" +      //new color
                "\n1" +         //change smth? - yes
                "\n7" +         //price
                "\n500" +       //new price
                "\n2";          //change smth? - no

        InputStream in = new ByteArrayInputStream(s.getBytes());
        System.setIn(in);
        ConsoleView.setReader(new BufferedReader(new InputStreamReader(System.in)));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(output);
        System.setOut(out);

        BikeController.start(null);
        int startQuantity = BikeRepository.getInstance().findAll().size();
        BikeController.execute(CommandType.ADD_NEW_SPEEDELEC);

        Assert.assertEquals(startQuantity, BikeRepository.getInstance().findAll().size() - 1);
        Assert.assertTrue(output.toString().contains("New bike characteristics: " +
                "\nBrand - NewBrand" +
                "\nMax speed (km/h) - 45" +
                "\nWeight (in grams) - 13000" +
                "\nHas lights - true" +
                "\nBattery capacity in mAh - 25000" +
                "\nColor - blue" +
                "\nPrice - 500"));
        Assert.assertTrue(output.toString().contains("New bike has been saved."));
    }

    @Test
    public void addNewSpeedelecWithValidationCheck() {
        String s = " " +        //brand name - empty - try again
                "\nBrandName" + //valid brand name
                "\n-1" +        //max speed - negative number - try again
                "\n0" +         //max speed - zero - try again
                "\n1000" +      //max speed - unreal number - try again
                "\n50" +        //valid max speed
                "\n-1" +        //weight - negative number - try again
                "\n0" +         //weight - zero - try again
                "\n1234567" +   //weight - unreal number - try again
                "\n12000" +     //valid weight
                "\nblah" +      //lights - invalid string - try again
                "\n1" +         //has lights
                "\n-1" +        //battery capacity - negative number - try again
                "\n0" +         //battery capacity - zero - try again
                "\n1234567" +   //battery capacity - unreal number - try again
                "\n10000" +     //valid battery capacity
                "\n   " +       //color - empty string - try again
                "\nwhite" +     //valid color
                "\n-1" +        //price - negative number - try again
                "\n1234567" +   //price - unreal number - try again
                "\n1000" +      //valid price
                "\n2";          //change smth? - no

        InputStream in = new ByteArrayInputStream(s.getBytes());
        System.setIn(in);
        ConsoleView.setReader(new BufferedReader(new InputStreamReader(System.in)));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(output);
        System.setOut(out);

        BikeController.start(null);
        int startQuantity = BikeRepository.getInstance().findAll().size();
        BikeController.execute(CommandType.ADD_NEW_SPEEDELEC);

        Assert.assertEquals(startQuantity, BikeRepository.getInstance().findAll().size() - 1);
        Assert.assertTrue(output.toString().contains("New bike characteristics: " +
                "\nBrand - BrandName" +
                "\nMax speed (km/h) - 50" +
                "\nWeight (in grams) - 12000" +
                "\nHas lights - true" +
                "\nBattery capacity in mAh - 10000" +
                "\nColor - white" +
                "\nPrice - 1000"));
        Assert.assertTrue(output.toString().contains("New bike has been saved."));
    }
}
