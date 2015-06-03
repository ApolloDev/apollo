
package edu.pitt.apollo.exception;

/**
 *
 * @author nem41
 */
public class UserNotAuthenticatedException extends DataServiceException {
	
	public UserNotAuthenticatedException(String message) {
		super(message);
	}
	
}
