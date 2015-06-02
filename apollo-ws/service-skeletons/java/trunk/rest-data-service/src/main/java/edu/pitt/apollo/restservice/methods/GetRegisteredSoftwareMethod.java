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
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v3_0_0.SerializationFormat;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.SerializerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class GetRegisteredSoftwareMethod {

	public static String getRegisteredSoftware(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException, SerializationException {

		DataServiceImpl impl = new DataServiceImpl();

		Authentication authentication = new Authentication();
		authentication.setRequesterId(username);
		authentication.setRequesterPassword(password);

		ResponseMessageBuilder responseBuilder = new ResponseMessageBuilder();

		Serializer serializer = SerializerFactory.getSerializer(serializationFormat, Serializer.APOLLO_NAMESPACE, Serializer.APOLLO_NAMESPACE_TNS_PREFIX);

		try {
			Map<Integer, ServiceRegistrationRecord> mapOfRecords = impl.getListOfRegisteredSoftwareRecords(authentication);

			List<String> serializedServiceRecords = new ArrayList<>();

			for (ServiceRegistrationRecord record : mapOfRecords.values()) {
				String serializedRecord = serializer.serializeObject(record);
				serializedServiceRecords.add(serializedRecord);
			}

			ObjectSerializationInformation serializationInformation = new ObjectSerializationInformation();
			serializationInformation.setClassNameSpace(Serializer.APOLLO_NAMESPACE);
			serializationInformation.setClassName(ServiceRegistrationRecord.class.getSimpleName());
			serializationInformation.setFormat(SerializationFormat.XML);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
					.setResponseBodySerializationInformation(serializationInformation).addContentToBody(serializedServiceRecords).setIsBodySerialized(true);

		} catch (DataServiceException | SerializationException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());

	}

}
