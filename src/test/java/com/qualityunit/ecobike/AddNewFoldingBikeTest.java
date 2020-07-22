package com.qualityunit.ecobike;

import com.qualityunit.ecobike.controller.BikeController;
import com.qualityunit.ecobike.controller.command.CommandType;
import com.qualityunit.ecobike.repository.BikeRepository;
import com.qualityunit.ecobike.view.ConsoleView;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class AddNewFoldingBikeTest {

    @Test
    public void addNewFoldingBike() {
        String s ="FoldingBike" +     //brand name
                "\n40" +              //wheels size
                "\n6" +               //gears
                "\n20000" +           //weight
                "\n1" +               //has lights
                "\ngreen" +           //color
                "\n899" +             //price
                "\n2";                //change smth? - no

        ByteArrayOutputStream output = TestUtil.setInputAndGetOutput(s);

        BikeController.start(null);
        int startQuantity = BikeRepository.getInstance().findAll().size();
        BikeController.execute(CommandType.ADD_NEW_FOLDING_BIKE);

        Assert.assertEquals(startQuantity, BikeRepository.getInstance().findAll().size() - 1);
        Assert.assertTrue(output.toString().contains("New bike characteristics: " +
                "\nBrand - FoldingBike" +
                "\nWheels size - 40" +
                "\nNumber of gears - 6" +
                "\nWeight (in grams) - 20000" +
                "\nHas lights - true" +
                "\nColor - green" +
                "\nPrice - 899"));
        Assert.assertTrue(output.toString().contains("New bike has been saved."));
    }

    @Test
    public void addNewFoldingBikeWithChanges() {
        String s ="FoldingBike" +     //brand name
                "\n40" +              //wheels size
                "\n6" +               //gears
                "\n20000" +           //weight
                "\n1" +               //has lights
                "\ngreen" +           //color
                "\n899" +             //price
                "\n1" +               //change smth? - yes
                "\n1" +               //brand name
                "\nNewBrand" +        //new brand name
                "\n1" +               //change smth? - yes
                "\n2" +               //wheels size
                "\n41" +              //new wheels size
                "\n1" +               //change smth? - yes
                "\n3" +               //gears
                "\n5" +               //new gears number
                "\n1" +               //change smth? - yes
                "\n4" +               //weight
                "\n21000" +           //new weight
                "\n1" +               //change smth? - yes
                "\n5" +               //lights availability
                "\n2" +               //no lights
                "\n1" +               //change smth? - yes
                "\n6" +               //color
                "\nyellow" +          //new color
                "\n1" +               //change smth? - yes
                "\n7" +               //price
                "\n900" +             //new price
                "\n2";                //change smth? - no

        ByteArrayOutputStream output = TestUtil.setInputAndGetOutput(s);

        BikeController.start(null);
        int startQuantity = BikeRepository.getInstance().findAll().size();
        BikeController.execute(CommandType.ADD_NEW_FOLDING_BIKE);

        Assert.assertEquals(startQuantity, BikeRepository.getInstance().findAll().size() - 1);
        Assert.assertTrue(output.toString().contains("New bike characteristics: " +
                "\nBrand - NewBrand" +
                "\nWheels size - 41" +
                "\nNumber of gears - 5" +
                "\nWeight (in grams) - 21000" +
                "\nHas lights - false" +
                "\nColor - yellow" +
                "\nPrice - 900"));
        Assert.assertTrue(output.toString().contains("New bike has been saved."));
    }

    @Test
    public void addNewFoldingBikeWithValidationCheck() {
        String s = "   " +            //brand name - empty - try again
                "\nBrandName" +       //valid brand name
                "\n-1" +              //wheels size - negative number - try again
                "\n0" +               //wheels size - zero - try again
                "\n500" +             //wheels size - unreal size - try again
                "\n40" +              //valid wheels size
                "\n-1" +              //gears - negative number - try again
                "\n0" +               //gears - zero - try again
                "\n1000" +            //gears - unreal number - try again
                "\n8" +               //valid gears number
                "\n-1" +              //weight - negative number - try again
                "\n0" +               //weight - zero - try again
                "\n1234567" +         //weight - unreal number - try again
                "\n20000" +           //valid weight
                "\nblah" +            //lights - invalid string - try again
                "\n1" +               //has lights
                "\n " +               //color - empty string - try again
                "\nwhite" +           //valid color
                "\n-1" +              //price - negative number - try again
                "\n1234567" +         //price - unreal number - try again
                "\n1000" +            //valid price
                "\n2";                //change smth? - no

        ByteArrayOutputStream output = TestUtil.setInputAndGetOutput(s);

        BikeController.start(null);
        int startQuantity = BikeRepository.getInstance().findAll().size();
        BikeController.execute(CommandType.ADD_NEW_FOLDING_BIKE);

        Assert.assertEquals(startQuantity, BikeRepository.getInstance().findAll().size() - 1);
        Assert.assertTrue(output.toString().contains("New bike characteristics: " +
                        "\nBrand - BrandName" +
                        "\nWheels size - 40" +
                        "\nNumber of gears - 8" +
                        "\nWeight (in grams) - 20000" +
                        "\nHas lights - true" +
                        "\nColor - white" +
                        "\nPrice - 1000"));
        Assert.assertTrue(output.toString().contains("New bike has been saved."));
    }
}
