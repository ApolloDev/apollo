package edu.pitt.apollo.restservice.rest.statuscodesandmessages;

/**
 * Created by dcs27 on 5/6/15.
 */
public class RequestSuccessfulMessage {
    static Integer status = 200;
    static String message = "OK";

    public static Integer getStatus() {
        return status;
    }

//    public static void setStatus(Integer status) {
//        this.status = status;
//    }

    public static String getMessage() {
        return message;
    }

//    public static void setMessage(String message) {
//        this.message = message;
//    }
}
