/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.runmanagerservicerestfrontend.methods;

import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.runmanagerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v4_0_1.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;
import edu.pitt.isg.objectserializer.Serializer;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class GetLastServiceToBeCalledForRunMethod extends BaseRunManagerServiceAccessorMethod {

	public GetLastServiceToBeCalledForRunMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, authorizationHeader);
	}
	
	public String getLastServiceToBeCalledForRun(BigInteger runId) throws UnsupportedSerializationFormatException, SerializationException {

		try {
			SoftwareIdentification softwareId;
			softwareId = impl.getLastServiceToBeCalledForRun(runId, authentication);

			ObjectSerializationInformation serializationInformation = new ObjectSerializationInformation();
			serializationInformation.setClassNameSpace(Serializer.APOLLO_NAMESPACE);
			serializationInformation.setClassName(softwareId.getClass().getSimpleName());
			serializationInformation.setFormat(SerializationFormat.XML);

			String serializedObject = serializer.serializeObject(softwareId);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
					.setResponseBodySerializationInformation(serializationInformation).addContentToBody(serializedObject).setIsBodySerialized(true);

		} catch (RunManagementException | SerializationException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
		
	}
	
}
