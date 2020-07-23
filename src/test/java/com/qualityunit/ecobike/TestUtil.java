package com.qualityunit.ecobike;

import com.qualityunit.ecobike.view.ConsoleView;

import java.io.*;

public class TestUtil {

    public static ByteArrayOutputStream getOutput() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(output);
        System.setOut(out);
        return output;
    }

    public static void setInput(String s) {
        InputStream in = new ByteArrayInputStream(s.getBytes());
        System.setIn(in);
        ConsoleView.setReader(new BufferedReader(new InputStreamReader(System.in)));
    }
}
