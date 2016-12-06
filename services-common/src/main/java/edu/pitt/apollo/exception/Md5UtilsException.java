package edu.pitt.apollo.exception;

import edu.pitt.apollo.types.v4_0_1.ExpectedUtility;

/**
 * Created by jdl50 on 5/18/15.
 */
public class Md5UtilsException extends Exception {
    public Md5UtilsException() {
        super();
    }

    public Md5UtilsException(String message) {
        super(message);
    }

    public Md5UtilsException(String message, Throwable cause) {
        super(message, cause);
    }

    public Md5UtilsException(Throwable cause) {
        super(cause);
    }

    protected Md5UtilsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
