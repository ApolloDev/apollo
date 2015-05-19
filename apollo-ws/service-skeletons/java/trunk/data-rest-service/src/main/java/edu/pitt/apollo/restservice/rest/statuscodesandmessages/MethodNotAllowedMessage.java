package edu.pitt.apollo.restservice.rest.statuscodesandmessages;

/**
 * Created by dcs27 on 5/15/15.
 */
public class MethodNotAllowedMessage {
    static Integer status = 405;
    static String message = "Method Not Allowed";

    public static Integer getStatus() {
        return status;
    }

//    public static void setStatus(Integer status) {
//        this.status = status;
//    }

    public static String getMessage() {
        return message;
    }
}
