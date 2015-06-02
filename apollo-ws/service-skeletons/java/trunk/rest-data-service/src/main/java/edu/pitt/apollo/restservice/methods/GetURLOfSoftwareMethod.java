/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.restservice.methods;

import edu.pitt.apollo.DataServiceImpl;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.restservice.rest.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v3_0_0.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.SerializationFormat;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.SerializerFactory;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class GetURLOfSoftwareMethod {

	public static String getURLOfSoftwareMethod(String username, String password, String softwareName, String softwareVersion,
			String softwareDeveloper, ApolloSoftwareTypeEnum softwareType, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException, SerializationException {

		DataServiceImpl impl = new DataServiceImpl();

		Authentication authentication = new Authentication();
		authentication.setRequesterId(username);
		authentication.setRequesterPassword(password);

		ResponseMessageBuilder responseBuilder = new ResponseMessageBuilder();

		Serializer serializer = SerializerFactory.getSerializer(serializationFormat, Serializer.APOLLO_NAMESPACE, Serializer.APOLLO_NAMESPACE_TNS_PREFIX);

		SoftwareIdentification softwareId = new SoftwareIdentification();
		softwareId.setSoftwareDeveloper(softwareDeveloper);
		softwareId.setSoftwareName(softwareName);
		softwareId.setSoftwareType(softwareType);
		softwareId.setSoftwareVersion(softwareVersion);

		try {
			String url = impl.getURLForSoftwareIdentification(softwareId, authentication);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
					.addContentToBody(url).setIsBodySerialized(false);

		} catch (DataServiceException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}

}
