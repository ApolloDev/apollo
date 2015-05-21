package edu.pitt.apollo.restservice.exceptions;

/**
 * Created by dcs27 on 5/5/15.
 */
public class SchemaDefinitionNotFoundException extends Exception {

    private static String message = "Error: No schema definition was found in the provided XML.";

    public static String getErrorMessage()
    {
        return message;
    }
}
