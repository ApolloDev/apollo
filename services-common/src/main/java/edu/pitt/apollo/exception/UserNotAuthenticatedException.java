
package edu.pitt.apollo.exception;

/**
 *
 * @author nem41
 */
public class UserNotAuthenticatedException extends DatastoreException {
	
	public UserNotAuthenticatedException(String message) {
		super(message);
	}
	
}
