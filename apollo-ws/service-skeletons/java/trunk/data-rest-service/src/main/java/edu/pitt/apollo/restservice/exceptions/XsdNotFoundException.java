package edu.pitt.apollo.restservice.exceptions;

/**
 * Created by dcs27 on 5/5/15.
 */
public class XsdNotFoundException extends Exception {

    String message;
    public XsdNotFoundException(String xsdVersion)
    {
        this.message = "Error: XSD version " +xsdVersion+" in provided XML was not found.";
    }



    public String getMessage()
    {
        return message;
    }
}
