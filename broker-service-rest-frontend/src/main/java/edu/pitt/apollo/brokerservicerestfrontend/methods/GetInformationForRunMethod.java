/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.brokerservicerestfrontend.methods;

import edu.pitt.apollo.data_service_types.v4_0_1.RunInformation;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import edu.pitt.apollo.brokerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.services_common.v4_0_1.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;
import edu.pitt.isg.objectserializer.Serializer;
import java.math.BigInteger;

import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class GetInformationForRunMethod extends BaseBrokerServiceAccessorMethod {

	public GetInformationForRunMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, authorizationHeader);
	}

	public String getInformationForRun(BigInteger runId) throws SerializationException, UnsupportedSerializationFormatException {

		try {

			RunInformation runInformation = new RunInformation();
			SoftwareIdentification softwareId = impl.getSoftwareIdentificationForRun(runId, authentication);
			runInformation.setSoftwareIdentification(softwareId);
			// possibly add more thing to runInformation in the future?

			String serializedObject = serializer.serializeObject(runInformation);

			ObjectSerializationInformation serializationInformation = new ObjectSerializationInformation();
			serializationInformation.setClassNameSpace(Serializer.APOLLO_NAMESPACE);
			serializationInformation.setClassName(runInformation.getClass().getSimpleName());
			serializationInformation.setFormat(SerializationFormat.XML);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
					.setResponseBodySerializationInformation(serializationInformation).addContentToBody(serializedObject).setIsBodySerialized(true);

		} catch (RunManagementException | SerializationException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}

}
