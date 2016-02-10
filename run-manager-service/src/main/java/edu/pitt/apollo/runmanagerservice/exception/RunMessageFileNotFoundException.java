package edu.pitt.apollo.runmanagerservice.exception;

import edu.pitt.apollo.exception.DatastoreException;

/**
 * Created by nem41 on 9/2/15.
 */
public class RunMessageFileNotFoundException extends DatastoreException {
    public RunMessageFileNotFoundException(String message) {
        super(message);
    }
}
