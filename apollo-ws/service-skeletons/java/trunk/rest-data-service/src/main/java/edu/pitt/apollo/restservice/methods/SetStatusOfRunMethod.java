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
import edu.pitt.apollo.restservice.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.SerializationFormat;
import edu.pitt.apollo.utilities.SerializerFactory;
import edu.pitt.apollo.utilities.Serializer;
import java.math.BigInteger;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class SetStatusOfRunMethod {

	public static String setStatusOfRun(String username, String password, BigInteger runId, MethodCallStatusEnum statusEnum,
			String message, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException, SerializationException {
		ResponseMessageBuilder responseBuilder = new ResponseMessageBuilder();

		Serializer serializer = SerializerFactory.getSerializer(serializationFormat, Serializer.APOLLO_NAMESPACE, Serializer.APOLLO_NAMESPACE_TNS_PREFIX);
		if (message.equalsIgnoreCase("") || message.trim().equalsIgnoreCase("")) {
			responseBuilder.setStatus(HttpStatus.BAD_REQUEST, "A valid status message is required.");
		} else {

			Authentication authentication = new Authentication();
			authentication.setRequesterId(username);
			authentication.setRequesterPassword(password);

			DataServiceImpl impl = new DataServiceImpl();

			try {
				impl.updateStatusOfRun(runId, statusEnum, message, authentication);

				responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE);
			} catch (DataServiceException ex) {
				responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
			}
		}

		return serializer.serializeObject(responseBuilder.getResponse());

	}

}
