/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.brokerservicerestfrontend.methods;

import edu.pitt.apollo.brokerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.services_common.v3_0_2.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v3_0_2.SerializationFormat;
import edu.pitt.apollo.services_common.v3_0_2.ServiceRegistrationRecord;
import edu.pitt.apollo.utilities.Serializer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class GetRegisteredSoftwareMethod extends BaseBrokerServiceAccessorMethod {

	public GetRegisteredSoftwareMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat);
	}

	public String getRegisteredSoftware() throws UnsupportedSerializationFormatException, SerializationException {

		try {
			List<ServiceRegistrationRecord> listOfRecords = impl.getListOfRegisteredSoftwareRecords(authentication);

			List<String> serializedServiceRecords = new ArrayList<>();

			for (ServiceRegistrationRecord record : listOfRecords) {
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
