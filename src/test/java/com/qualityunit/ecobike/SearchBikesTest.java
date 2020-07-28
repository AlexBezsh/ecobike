package com.qualityunit.ecobike;

import com.qualityunit.ecobike.controller.BikeController;
import com.qualityunit.ecobike.controller.command.CommandType;
import com.qualityunit.ecobike.exception.InterruptOperationException;
import com.qualityunit.ecobike.model.ElectricBike;
import com.qualityunit.ecobike.model.FoldingBike;
import com.qualityunit.ecobike.model.Speedelec;
import com.qualityunit.ecobike.util.AppUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

public class SearchBikesTest {

    //each characteristic of these bikes must be as unique as possible
    //otherwise the program may ask how many items to show on one page
    private FoldingBike testFoldingBike = new FoldingBike(AppUtil.idGenerator.getAndIncrement(),
            "This is a folding bike test"                    //brand name
            , 199                                            //wheels size
            , 99                                             //gears
            , 999999                                         //weight
            , true                                           //has lights
            , "mottled with white strips and skyblue dots"   //color
            , 999999);                                       //price
    private Speedelec testSpeedelec = new Speedelec(AppUtil.idGenerator.getAndIncrement(),
            "This is a speedelec test"                       //brand name
            , 499                                            //max speed
            , 999998                                         //weight
            , true                                           //has lights
            , 999999                                         //battery capacity
            , "mottled with orange strips and skyblue dots"  //color
            , 999998);                                       //price
    private ElectricBike testElectricBike = new ElectricBike(AppUtil.idGenerator.getAndIncrement(),
            "This is an electric bike test"                 //brand name
            , 498                                            //max speed
            , 999997                                         //weight
            , true                                           //has lights
            , 999998                                         //battery capacity
            , "mottled with green strips and skyblue dots"   //color
            , 999997);                                       //price

    @BeforeClass
    public static void start() {     //add new bikes and then search for them by various criteria
        String foldingBike = "This is a folding bike test" +
                "\n199" +
                "\n99" +
                "\n999999" +
                "\n1" +
                "\nmottled with white strips and skyblue dots" +
                "\n999999" +
                "\n2";       //change smth? - no
        String speedelec = "This is a speedelec test" +
                "\n499" +
                "\n999998" +
                "\n1" +
                "\n999999" +
                "\nmottled with orange strips and skyblue dots" +
                "\n999998" +
                "\n2";       //change smth? - no
        String electricBike = "This is an electric bike test" +
                "\n498" +
                "\n999997" +
                "\n1" +
                "\n999998" +
                "\nmottled with green strips and skyblue dots" +
                "\n999997" +
                "\n2";       //change smth? - no

        TestUtil.setInput(foldingBike);
        BikeController.start(null);
        BikeController.execute(CommandType.ADD_NEW_FOLDING_BIKE);

        TestUtil.setInput(speedelec);
        BikeController.execute(CommandType.ADD_NEW_SPEEDELEC);

        TestUtil.setInput(electricBike);
        BikeController.execute(CommandType.ADD_NEW_ELECTRIC_BIKE);
    }


    @Test
    public void searchFoldingBikeByBrand() {
        String s = "1" +                           //type of bike - folding
                "\nThis is a folding bike test" +  //brand name
                "\nany" +                          //weight
                "\nany" +                          //color
                "\nany" +                          //price
                "\n2" +                            //lights not important
                "\nany" +                          //number of gears
                "\nany" +                          //wheels size
                "\nexit";                          //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testFoldingBike.toString()));
    }

    @Test
    public void searchFoldingBikeByWeight() {
        String s = "1" +              //type of bike - folding
                "\nany" +             //brand name
                "\na" +               //random symbol - to open the weight form
                "\n999999" +          //from
                "\n999999" +          //to
                "\nany" +             //color
                "\nany" +             //price
                "\n2" +               //lights not important
                "\nany" +             //number of gears
                "\nany" +             //wheels size
                "\nexit";             //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testFoldingBike.toString()));
    }

    @Test
    public void searchFoldingBikeByColor() {
        String s = "1" +                                         //type of bike - folding
                "\nany" +                                        //brand name
                "\nany" +                                        //weight
                "\nmottled with white strips and skyblue dots" + //color
                "\nany" +                                        //price
                "\n2" +                                          //lights not important
                "\nany" +                                        //number of gears
                "\nany" +                                        //wheels size
                "\nexit";                                        //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testFoldingBike.toString()));
    }

    @Test
    public void searchFoldingBikeByPrice() {
        String s = "1" +        //type of bike - folding
                "\nany" +       //brand name
                "\nany" +       //weight
                "\nany" +       //color
                "\na" +         //random symbol - to open the price form
                "\n999999" +    //from
                "\n999999" +    //to
                "\n2" +         //lights not important
                "\nany" +       //number of gears
                "\nany" +       //wheels size
                "\nexit";       //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testFoldingBike.toString()));
    }

    @Test
    public void searchFoldingBikeByPriceAndLights() {
        String s = "1" +        //type of bike - folding
                "\nany" +       //brand name
                "\nany" +       //weight
                "\nany" +       //color
                "\na" +         //random symbol - to open the price form
                "\n999999" +    //from
                "\n999999" +    //to
                "\n1" +         //must have lights
                "\nany" +       //number of gears
                "\nany" +       //wheels size
                "\nexit";       //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testFoldingBike.toString()));
    }

    @Test
    public void searchFoldingBikeByNumberOfGears() {
        String s = "1" +        //type of bike - folding
                "\nany" +       //brand name
                "\nany" +       //weight
                "\nany" +       //color
                "\nany" +       //price
                "\n2" +         //lights not important
                "\na" +         //random symbol - to open the number of gears form
                "\n99" +        //from
                "\n99" +        //to
                "\nany" +       //wheels size
                "\nexit";       //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testFoldingBike.toString()));
    }

    @Test
    public void searchFoldingBikeByWheelsSize() {
        String s = "1" +        //type of bike - folding
                "\nany" +       //brand name
                "\nany" +       //weight
                "\nany" +       //color
                "\nany" +       //price
                "\n2" +         //lights not important
                "\nany" +       //number of gears
                "\na" +         //random symbol - to open the wheels size form
                "\n199" +       //from
                "\n199" +       //to
                "\nexit";       //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testFoldingBike.toString()));
    }

    @Test
    public void searchSpeedelecByBrand() {
        String s = "2" +                           //type of bike - speedelec
                "\nThis is a speedelec test" +     //brand name
                "\nany" +                          //max speed
                "\nany" +                          //weight
                "\nany" +                          //price
                "\n2" +                            //lights not important
                "\nany" +                          //battery capacity
                "\nany" +                          //max speed
                "\nexit";                          //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testSpeedelec.toString()));
    }

    @Test
    public void searchSpeedelecByWeight() {
        String s = "2" +              //type of bike - speedelec
                "\nany" +             //brand name
                "\na" +               //random symbol - to open the weight form
                "\n999998" +          //from
                "\n999998" +          //to
                "\nany" +             //color
                "\nany" +             //price
                "\n2" +               //lights not important
                "\nany" +             //battery capacity
                "\nany" +             //max speed
                "\nexit";             //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testSpeedelec.toString()));
    }

    @Test
    public void searchSpeedelecByColor() {
        String s = "2" +                                          //type of bike - speedelec
                "\nany" +                                         //brand name
                "\nany" +                                         //weight
                "\nmottled with orange strips and skyblue dots" + //color
                "\nany" +                                         //price
                "\n2" +                                           //lights not important
                "\nany" +                                         //battery capacity
                "\nany" +                                         //max speed
                "\nexit";                                         //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testSpeedelec.toString()));
    }

    @Test
    public void searchSpeedelecByPrice() {
        String s = "2" +        //type of bike - speedelec
                "\nany" +       //brand name
                "\nany" +       //weight
                "\nany" +       //color
                "\na" +         //random symbol - to open the price form
                "\n999998" +    //from
                "\n999998" +    //to
                "\n2" +         //lights not important
                "\nany" +       //battery capacity
                "\nany" +       //max speed
                "\nexit";       //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testSpeedelec.toString()));
    }

    @Test
    public void searchSpeedelecByPriceAndLights() {
        String s = "2" +        //type of bike - speedelec
                "\nany" +       //brand name
                "\nany" +       //weight
                "\nany" +       //color
                "\na" +         //random symbol - to open the price form
                "\n999998" +    //from
                "\n999998" +    //to
                "\n1" +         //must have lights
                "\nany" +       //battery capacity
                "\nany" +       //max speed
                "\nexit";       //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testSpeedelec.toString()));
    }

    @Test
    public void searchSpeedelecByBatteryCapacity() {
        String s = "2" +        //type of bike - speedelec
                "\nany" +       //brand name
                "\nany" +       //weight
                "\nany" +       //color
                "\nany" +       //price
                "\n2" +         //lights not important
                "\na" +         //random symbol - to open the battery capacity form
                "\n999999" +    //from
                "\n999999" +    //to
                "\nany" +       //max speed
                "\nexit";       //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testSpeedelec.toString()));
    }

    @Test
    public void searchSpeedelecByMaxSpeed() {
        String s = "2" +        //type of bike - speedelec
                "\nany" +       //brand name
                "\nany" +       //weight
                "\nany" +       //color
                "\nany" +       //price
                "\n2" +         //lights not important
                "\nany" +       //battery capacity
                "\na" +         //random symbol - to open the max speed form
                "\n499" +       //from
                "\n499" +       //to
                "\nexit";       //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testSpeedelec.toString()));
    }

    @Test
    public void searchElectricBikeByBrand() {
        String s = "3" +                            //type of bike - electric bike
                "\nThis is an electric bike test" + //brand name
                "\nany" +                           //max speed
                "\nany" +                           //weight
                "\nany" +                           //price
                "\n2" +                             //lights not important
                "\nany" +                           //battery capacity
                "\nany" +                           //max speed
                "\nexit";                           //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testElectricBike.toString()));
    }

    @Test
    public void searchElectricBikeByWeight() {
        String s = "3" +              //type of bike - electric bike
                "\nany" +             //brand name
                "\na" +               //random symbol - to open the weight form
                "\n999997" +          //from
                "\n999997" +          //to
                "\nany" +             //color
                "\nany" +             //price
                "\n2" +               //lights not important
                "\nany" +             //battery capacity
                "\nany" +             //max speed
                "\nexit";             //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testElectricBike.toString()));
    }

    @Test
    public void searchElectricBikeByColor() {
        String s = "3" +                                          //type of bike - electric bike
                "\nany" +                                         //brand name
                "\nany" +                                         //weight
                "\nmottled with green strips and skyblue dots" +  //color
                "\nany" +                                         //price
                "\n2" +                                           //lights not important
                "\nany" +                                         //battery capacity
                "\nany" +                                         //max speed
                "\nexit";                                         //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testElectricBike.toString()));
    }

    @Test
    public void searchElectricBikeByPrice() {
        String s = "3" +        //type of bike - electric bike
                "\nany" +       //brand name
                "\nany" +       //weight
                "\nany" +       //color
                "\na" +         //random symbol - to open the price form
                "\n999997" +    //from
                "\n999997" +    //to
                "\n2" +         //lights not important
                "\nany" +       //battery capacity
                "\nany" +       //max speed
                "\nexit";       //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testElectricBike.toString()));
    }

    @Test
    public void searchElectricBikeByPriceAndLights() {
        String s = "3" +        //type of bike - electric bike
                "\nany" +       //brand name
                "\nany" +       //weight
                "\nany" +       //color
                "\na" +         //random symbol - to open the price form
                "\n999997" +    //from
                "\n999997" +    //to
                "\n1" +         //must have lights
                "\nany" +       //battery capacity
                "\nany" +       //max speed
                "\nexit";       //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testElectricBike.toString()));
    }

    @Test
    public void searchElectricBikeByBatteryCapacity() {
        String s = "3" +        //type of bike - electric bike
                "\nany" +       //brand name
                "\nany" +       //weight
                "\nany" +       //color
                "\nany" +       //price
                "\n2" +         //lights not important
                "\na" +         //random symbol - to open the battery capacity form
                "\n999998" +    //from
                "\n999998" +    //to
                "\nany" +       //max speed
                "\nexit";       //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testElectricBike.toString()));
    }

    @Test
    public void searchElectricBikeByMaxSpeed() {
        String s = "3" +        //type of bike - electric bike
                "\nany" +       //brand name
                "\nany" +       //weight
                "\nany" +       //color
                "\nany" +       //price
                "\n2" +         //lights not important
                "\nany" +       //battery capacity
                "\na" +         //random symbol - to open the max speed form
                "\n498" +       //from
                "\n498" +       //to
                "\nexit";       //InterruptOperationException will be thrown

        TestUtil.setInput(s);
        ByteArrayOutputStream output = TestUtil.getOutput();

        BikeController.start(null);
        try {
            BikeController.execute(CommandType.SEARCH_BIKES);
        } catch (InterruptOperationException ignore) {
        }
        Assert.assertTrue(output.toString().contains(testElectricBike.toString()));
    }
}
