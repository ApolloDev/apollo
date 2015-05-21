package edu.pitt.apollo.restservice.exceptions;

/**
 * Created by dcs27 on 5/18/15.
 */
public class ParsingFromXmlToObjectException extends Exception {
    private String message;

    public ParsingFromXmlToObjectException(String message)
    {
        super(message);
    }
    public String getErrorMessage()
    {
        return message;
    }
    public void setErrorMessage(String message)
    {
        this.message = message;
    }
}
