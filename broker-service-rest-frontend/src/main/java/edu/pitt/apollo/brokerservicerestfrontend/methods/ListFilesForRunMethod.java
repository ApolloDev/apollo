/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.brokerservicerestfrontend.methods;

import edu.pitt.apollo.brokerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.services_common.v4_0.ObjectSerializationInformation;
;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.utilities.Serializer;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */


public class ListFilesForRunMethod extends BaseBrokerServiceAccessorMethod {

	public ListFilesForRunMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, authorizationHeader);
	}

	public String listFilesForRun(BigInteger runID) throws UnsupportedSerializationFormatException, SerializationException {

		try {
			List<FileIdentification> fileIds = impl.listFilesForRun(runID, authentication);

			ObjectSerializationInformation serializationInformation = new ObjectSerializationInformation();
			serializationInformation.setClassNameSpace(Serializer.APOLLO_NAMESPACE);
			serializationInformation.setClassName(fileIds.getClass().getSimpleName());
			serializationInformation.setFormat(serializationFormat);

			List<String> serializedObjects = new ArrayList<>();
			for (FileIdentification id : fileIds) {
				String serializedObject = serializer.serializeObject(id);
				serializedObjects.add(serializedObject);
			}

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
					.setResponseBodySerializationInformation(serializationInformation).addContentToBody(serializedObjects).setIsBodySerialized(true);

		} catch (FilestoreException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}

}
