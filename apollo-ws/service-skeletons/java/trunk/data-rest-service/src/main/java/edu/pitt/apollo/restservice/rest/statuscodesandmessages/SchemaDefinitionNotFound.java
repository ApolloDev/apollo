package edu.pitt.apollo.restservice.rest.statuscodesandmessages;

/**
 * Created by dcs27 on 5/6/15.
 */
public class SchemaDefinitionNotFound {
    Integer status = 415;
    String message = "Error: No schema definition was found in the provided XML.";

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
