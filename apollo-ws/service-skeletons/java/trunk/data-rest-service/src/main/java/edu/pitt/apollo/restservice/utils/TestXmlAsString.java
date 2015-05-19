package edu.pitt.apollo.restservice.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by dcs27 on 5/5/15.
 */
public class TestXmlAsString {


    public static String getTestXml(boolean isIds) throws Exception
    {
        if(!isIds) {
            String xml = "";
            ClassLoader cLoader = TestXmlAsString.class.getClassLoader();
            InputStream epidemicXmlInputStream = cLoader.getResourceAsStream("./epidemic.xml");

            Scanner scanner = new Scanner(epidemicXmlInputStream);
            String line;
            while (scanner.hasNext()) {
                xml += scanner.nextLine();
            }
            return xml;
        }
        else{
            String xml = "";
            ClassLoader cLoader = TestXmlAsString.class.getClassLoader();
            InputStream epidemicXmlInputStream = cLoader.getResourceAsStream("./ids.xml");

            Scanner scanner = new Scanner(epidemicXmlInputStream);
            String line;
            while (scanner.hasNext()) {
                xml += scanner.nextLine();
            }
            return xml;
        }
    }
}
