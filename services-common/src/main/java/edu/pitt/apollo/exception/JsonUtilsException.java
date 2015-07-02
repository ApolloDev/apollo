package edu.pitt.apollo.exception;

/**
 * Created by jdl50 on 5/15/15.
 */
public class JsonUtilsException extends Exception {
    public JsonUtilsException() {
        super();
    }

    public JsonUtilsException(String message) {
        super(message);
    }

    public JsonUtilsException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonUtilsException(Throwable cause) {
        super(cause);
    }

    protected JsonUtilsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
