/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.runmanagerservicerestfrontend.methods;

import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import edu.pitt.apollo.runmanagerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import java.math.BigInteger;

import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class StartRunMethod extends BaseRunManagerServiceAccessorMethod {

	public StartRunMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, authorizationHeader);
	}

	public String startRun(BigInteger runId) throws SerializationException, UnsupportedSerializationFormatException {

		try {
			impl.run(runId, authentication);
			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE);
		} catch (JobRunningServiceException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}

}
