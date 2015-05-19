package edu.pitt.apollo.restservice.rest.statuscodesandmessages;

/**
 * Created by dcs27 on 5/15/15.
 */
public class NotAuthorizedMessage {
    static Integer status = 401;
    static String message = "User must be authorized to complete task.";

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
