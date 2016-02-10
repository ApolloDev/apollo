package edu.pitt.apollo.exception;

/**
 *
 * @author nem41
 */
public class UserNotAuthorizedException extends DatastoreException {

	public UserNotAuthorizedException(String message) {
		super(message);
	}

}
