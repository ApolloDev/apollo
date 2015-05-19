package edu.pitt.apollo.restservice.rest.statuscodesandmessages;

/**
 * Created by dcs27 on 5/6/15.
 */
public class XsdNotFound {
    Integer status = 404;
    String message = "Error: XSD version in provided XML was not found. Available XSD versions can be found with a GET request to /apolloXsdVersions";

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
