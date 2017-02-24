package edu.pitt.apollo.apollotranslator;

import edu.pitt.isg.objectserializer.XMLDeserializer;
import edu.pitt.isg.objectserializer.exceptions.DeserializationException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * These are methods that we expose to JavaScript. Note, the methods are not
 * actually exposed here, they are just impelmented here. We make these methods
 * visable to the JavaScript in the addDefaultCustomMethodsToScope function in
 * the ApolloTranslator class.
 * 
 */
public class JavaScriptAccessibleMethods {

	public static void printline(String s) {
		System.out.println(s);
	}

	public static String readFile(String fileName) throws IOException {
		return FileUtils.readFileToString(new File(fileName));
	}


    public static Object parseApolloObjectFromXml(String xml, String name, String nameSpace) {

        XMLDeserializer xmlDeserializer = new XMLDeserializer();
        try {
            return xmlDeserializer.getObjectFromMessage(xml, name, nameSpace);
        } catch (DeserializationException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
	
}