package edu.pitt.apollo.restservice.utils;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by dcs27 on 5/5/15.
 */
public class TestJsonAsString {


    public static String getTestJson(boolean isIds) throws Exception
    {

            String json = "";
            ClassLoader cLoader = TestJsonAsString.class.getClassLoader();
            InputStream epidemicXmlInputStream = cLoader.getResourceAsStream("./idsWithControlStrats.json");

            Scanner scanner = new Scanner(epidemicXmlInputStream);
            String line;
            while (scanner.hasNext()) {
                json += scanner.nextLine();
            }
            return json;

    }
}
