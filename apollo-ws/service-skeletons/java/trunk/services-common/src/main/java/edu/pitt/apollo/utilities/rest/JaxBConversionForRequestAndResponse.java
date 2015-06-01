package edu.pitt.apollo.utilities.rest;

import javax.xml.bind.JAXB;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by dcs27 on 5/29/15.
 */
public class JaxBConversionForRequestAndResponse {
    public static Object getObjectFromXmlMessage(String xml,String className) throws Exception {
        Class<?> classToParseTo = null;
        try {
            classToParseTo = Class.forName(className);
            InputStream xmlAsStream = new ByteArrayInputStream(xml.getBytes());
            Object objectToReturn = JAXB.unmarshal(xmlAsStream, classToParseTo);
            return objectToReturn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new Exception("Could not parse object");
        }

    }
}
