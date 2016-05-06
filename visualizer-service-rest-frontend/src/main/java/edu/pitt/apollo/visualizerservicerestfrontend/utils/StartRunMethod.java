/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.visualizerservicerestfrontend.utils;

import edu.pitt.apollo.VisualizerServiceImpl;
import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.SerializerFactory;
import java.math.BigInteger;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class StartRunMethod {

	protected static final ApolloServiceQueue serviceQueue = new ApolloServiceQueue();
	protected final Authentication authentication;
	protected final Serializer serializer;
	protected final ResponseMessageBuilder responseBuilder;

	public StartRunMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		authentication = new Authentication();
		authentication.setRequesterId(username);
		authentication.setRequesterPassword(password);

		responseBuilder = new ResponseMessageBuilder();
		serializer = SerializerFactory.getSerializer(serializationFormat);

	}

	public String startRun(BigInteger runId) throws SerializationException, UnsupportedSerializationFormatException, FilestoreException, RunManagementException {

		VisualizerServiceImpl impl = new VisualizerServiceImpl();

		responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE);

		return serializer.serializeObject(responseBuilder.getResponse());
	}

}
