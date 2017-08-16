/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.runmanagerservicerestfrontend.methods;

import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.runmanagerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v4_0_2.SerializationFormat;
import edu.pitt.apollo.types.v4_0_2.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v4_0_2.SoftwareIdentification;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

;

/**
 *
 * @author nem41
 */
public class GetURLOfSoftwareMethod extends BaseRunManagerServiceAccessorMethod {

	public GetURLOfSoftwareMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, authorizationHeader);
	}

	public String getURLOfSoftwareMethod(String softwareName, String softwareVersion,
			String softwareDeveloper, ApolloSoftwareTypeEnum softwareType) throws UnsupportedSerializationFormatException, SerializationException {

		SoftwareIdentification softwareId = new SoftwareIdentification();
		softwareId.setSoftwareDeveloper(softwareDeveloper);
		softwareId.setSoftwareName(softwareName);
		softwareId.setSoftwareType(softwareType);
		softwareId.setSoftwareVersion(softwareVersion);

		try {
			String url = impl.getURLForSoftwareIdentification(softwareId, authentication);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
					.addContentToBody(url).setIsBodySerialized(false);

		} catch (DatastoreException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}

}
