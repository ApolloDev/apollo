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
import edu.pitt.apollo.utilities.SerializerFactory;
import edu.pitt.apollo.utilities.Serializer;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class GetRunIdsInSimulationGroupForRunMethod {

	public static String getRunIdsInSimulationGroupForRun(String username, String password, BigInteger runId, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException, SerializationException {

		DataServiceImpl impl = new DataServiceImpl();

		Authentication authentication = new Authentication();
		authentication.setRequesterId(username);
		authentication.setRequesterPassword(password);

		ResponseMessageBuilder responseBuilder = new ResponseMessageBuilder();

		Serializer serializer = SerializerFactory.getSerializer(serializationFormat, Serializer.APOLLO_NAMESPACE, Serializer.APOLLO_NAMESPACE_TNS_PREFIX);

		try {
			List<BigInteger> ids = impl.getRunIdsAssociatedWithSimulationGroupForRun(runId, authentication);

			ObjectSerializationInformation serializationInformation = new ObjectSerializationInformation();
			serializationInformation.setClassNameSpace(Serializer.APOLLO_NAMESPACE);
			serializationInformation.setClassName(ids.getClass().getSimpleName());
			serializationInformation.setFormat(SerializationFormat.XML);

			List<String> serializedObjects = new ArrayList<>();
			for (BigInteger id : ids) {
				String serializedObject = serializer.serializeObject(id);
				serializedObjects.add(serializedObject);
			}

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
					.setResponseBodySerializationInformation(serializationInformation).addContentToBody(serializedObjects).setIsBodySerialized(true);

		} catch (DataServiceException | SerializationException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}

}
