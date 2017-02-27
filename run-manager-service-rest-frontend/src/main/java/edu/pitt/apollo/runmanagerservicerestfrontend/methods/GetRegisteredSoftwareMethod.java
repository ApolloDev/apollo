/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.runmanagerservicerestfrontend.methods;

import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.runmanagerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v4_0_1.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import edu.pitt.apollo.services_common.v4_0_1.ServiceRecord;
import edu.pitt.apollo.services_common.v4_0_1.ServiceRegistrationRecord;
import edu.pitt.isg.objectserializer.Serializer;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nem41
 */
public class GetRegisteredSoftwareMethod extends BaseRunManagerServiceAccessorMethod {

	public GetRegisteredSoftwareMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, authorizationHeader);
	}

	public String getRegisteredSoftware() throws UnsupportedSerializationFormatException, SerializationException {

		try {
			List<ServiceRecord> listOfRecords = impl.getListOfRegisteredSoftwareRecords(authentication);

			List<String> serializedServiceRecords = new ArrayList<>();

			for (ServiceRecord record : listOfRecords) {
				String serializedRecord = serializer.serializeObject(record);
				serializedServiceRecords.add(serializedRecord);
			}

			ObjectSerializationInformation serializationInformation = new ObjectSerializationInformation();
			serializationInformation.setClassNameSpace(Serializer.APOLLO_NAMESPACE);
			serializationInformation.setClassName(ServiceRegistrationRecord.class.getSimpleName());
			serializationInformation.setFormat(SerializationFormat.XML);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
					.setResponseBodySerializationInformation(serializationInformation).addContentToBody(serializedServiceRecords).setIsBodySerialized(true);

		} catch (DatastoreException | SerializationException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());

	}

}
