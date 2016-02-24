package edu.pitt.apollo.outputtranslator.types.rest.statuscodesandmessages;



/**
 * Created by dcs27 on 4/22/15.
 */
public class ResourceNotFoundInCollection extends Exception{
    Integer errorCode = 200;
    String errorMessage = "Resource not found in collection";
    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
