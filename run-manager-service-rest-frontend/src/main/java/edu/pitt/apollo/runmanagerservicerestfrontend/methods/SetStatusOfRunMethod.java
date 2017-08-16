/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.runmanagerservicerestfrontend.methods;

import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.runmanagerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0_2.SerializationFormat;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class SetStatusOfRunMethod extends BaseRunManagerServiceAccessorMethod {

	public SetStatusOfRunMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, authorizationHeader);
	}

	public String setStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnum,
			String message) throws UnsupportedSerializationFormatException, SerializationException {

		if (message.equalsIgnoreCase("") || message.trim().equalsIgnoreCase("")) {
			responseBuilder.setStatus(HttpStatus.BAD_REQUEST, "A valid status message is required.");
		} else {

			try {
				impl.updateStatusOfRun(runId, statusEnum, message, authentication);

				responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE);
			} catch (RunManagementException ex) {
				responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
			}
		}

		return serializer.serializeObject(responseBuilder.getResponse());

	}

}
