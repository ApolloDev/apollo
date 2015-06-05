/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.restservice.methods;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.restservice.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v3_0_0.SerializationFormat;
import edu.pitt.apollo.utilities.Serializer;
import java.math.BigInteger;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class GetStatusOfRunMethod extends BaseRunManagerServiceAccessorMethod {

	public GetStatusOfRunMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat);
	}

	public String getStatusForRun(BigInteger runId) throws UnsupportedSerializationFormatException, SerializationException {

		try {
			MethodCallStatus status = impl.getRunStatus(runId, authentication);

			ObjectSerializationInformation objectSerializationInformation = new ObjectSerializationInformation();
			objectSerializationInformation.setClassNameSpace(Serializer.APOLLO_NAMESPACE);
			objectSerializationInformation.setClassName(status.getClass().getSimpleName());
			objectSerializationInformation.setFormat(SerializationFormat.XML);

			String serializedObject = serializer.serializeObject(status);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
					.setResponseBodySerializationInformation(objectSerializationInformation).addContentToBody(serializedObject).setIsBodySerialized(true);

		} catch (SerializationException | DataServiceException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());

	}

}
