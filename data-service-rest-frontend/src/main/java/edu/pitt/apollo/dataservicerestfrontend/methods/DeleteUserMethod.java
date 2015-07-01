/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.dataservicerestfrontend.methods;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.dataservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v3_0_0.SerializationFormat;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class DeleteUserMethod extends BaseDataServiceAccessorMethod {

	public DeleteUserMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat);
	}

	public String deleteUser(String usernameToDelete) throws UnsupportedSerializationFormatException, SerializationException {

		try {
			impl.deleteUser(usernameToDelete, authentication);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE);
		} catch (DataServiceException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}

}
