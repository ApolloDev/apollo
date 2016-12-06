/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.visualizerservicerestfrontend.utils;

import edu.pitt.apollo.VisualizerServiceImpl;
import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.exception.*;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import edu.pitt.apollo.utilities.ApolloClassList;
import edu.pitt.isg.objectserializer.DeserializerFactory;
import edu.pitt.isg.objectserializer.Serializer;
import edu.pitt.isg.objectserializer.SerializerFactory;
import java.math.BigInteger;
import java.util.Arrays;

import edu.pitt.apollo.utilities.AuthorizationUtility;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
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

	public StartRunMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		authentication = AuthorizationUtility.createAuthenticationFromAuthorizationHeader(authorizationHeader);

		responseBuilder = new ResponseMessageBuilder();


		switch (serializationFormat) {
			case JSON:
				serializer = SerializerFactory.getSerializer(edu.pitt.isg.objectserializer.SerializationFormat.JSON, Arrays.asList(ApolloClassList.classList));
				break;
			case XML:
				serializer = SerializerFactory.getSerializer(edu.pitt.isg.objectserializer.SerializationFormat.XML, Arrays.asList(ApolloClassList.classList));
				break;
			default:
				serializer = null;
				break;
		}


	}

	public String startRun(BigInteger runId) throws SerializationException, UnsupportedSerializationFormatException, FilestoreException, RunManagementException {

		VisualizerServiceImpl impl = new VisualizerServiceImpl();
        try {
            impl.run(runId, authentication);
            responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE);
        } catch (JobRunningServiceException ex) {
            responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

		return serializer.serializeObject(responseBuilder.getResponse());
	}

}
