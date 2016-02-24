package edu.pitt.apollo.outputtranslator.types.rest.statuscodesandmessages;

/**
 * Created by dcs27 on 4/22/15.
 */
public class MethodNotAllowedError {
    Integer errorCode = 405;
    String errorMessage = "Method Not Allowed";
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
