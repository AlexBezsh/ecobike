package com.qualityunit.ecobike;

import com.qualityunit.ecobike.view.ConsoleView;

import java.io.*;

public class TestUtil {

    public static ByteArrayOutputStream setInputAndGetOutput(String s) {
        InputStream in = new ByteArrayInputStream(s.getBytes());
        System.setIn(in);
        ConsoleView.setReader(new BufferedReader(new InputStreamReader(System.in)));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(output);
        System.setOut(out);
        return output;
    }
}
