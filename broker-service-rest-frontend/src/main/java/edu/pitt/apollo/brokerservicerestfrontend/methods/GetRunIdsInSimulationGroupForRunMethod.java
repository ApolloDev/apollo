/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.brokerservicerestfrontend.methods;

import edu.pitt.apollo.brokerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.services_common.v4_0_1.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import edu.pitt.isg.objectserializer.Serializer;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nem41
 */
public class GetRunIdsInSimulationGroupForRunMethod extends BaseBrokerServiceAccessorMethod {

	public GetRunIdsInSimulationGroupForRunMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, authorizationHeader);
	}

	public String getRunIdsInSimulationGroupForRun(BigInteger runId) throws UnsupportedSerializationFormatException, SerializationException {

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

		} catch (RunManagementException | SerializationException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}
}
