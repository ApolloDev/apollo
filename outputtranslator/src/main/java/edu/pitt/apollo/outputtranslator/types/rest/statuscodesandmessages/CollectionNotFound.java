package edu.pitt.apollo.outputtranslator.types.rest.statuscodesandmessages;

/**
 * Created by dcs27 on 4/22/15.
 */
public class CollectionNotFound {
    Integer errorCode = 404;
    String errorMessage = "Collection not found";
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
