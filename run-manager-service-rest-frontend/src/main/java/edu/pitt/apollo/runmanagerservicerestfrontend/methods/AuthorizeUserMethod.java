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
import edu.pitt.apollo.types.v4_0.ApolloSoftwareTypeEnum;;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import org.springframework.http.HttpStatus;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class AuthorizeUserMethod extends BaseRunManagerServiceAccessorMethod {

	public AuthorizeUserMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat);
	}
	
	public String authorizeUser(String username, String password, String softwareName, String softwareVersion,
			String softwareDeveloper, ApolloSoftwareTypeEnum softwareType, boolean requestToRunSoftware) throws SerializationException {
		Authentication authenticationForUser = new Authentication();
		authenticationForUser.setRequesterId(username);
		authenticationForUser.setRequesterPassword(password);
		
		SoftwareIdentification softwareId = new SoftwareIdentification();
		softwareId.setSoftwareDeveloper(softwareDeveloper);
		softwareId.setSoftwareName(softwareName);
		softwareId.setSoftwareType(softwareType);
		softwareId.setSoftwareVersion(softwareVersion);
		
		try {
			impl.authorizeUser(authenticationForUser, softwareId, requestToRunSoftware);
			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE);
		} catch (DatastoreException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}
	
}
