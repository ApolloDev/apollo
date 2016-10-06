package edu.pitt.securitymanager.exception;

/**
 * Created by nem41 on 10/5/16.
 */
public class UserNotAuthorizedException extends ApolloSecurityException {
    public UserNotAuthorizedException(String message) {
        super(message);
    }
}
