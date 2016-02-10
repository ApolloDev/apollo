/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.runmanagerservicerestfrontend.methods;

import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.runmanagerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class AuthenticateUserMethod extends BaseRunManagerServiceAccessorMethod {

	public AuthenticateUserMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat);
	}

	public String authenticateUser(String username, String password) throws SerializationException {
		Authentication authenticationForUser = new Authentication();
		authenticationForUser.setRequesterId(username);
		authenticationForUser.setRequesterPassword(password);
		
		try {
			impl.authenticateUser(authenticationForUser);
			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE);
		} catch (DatastoreException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}

}
