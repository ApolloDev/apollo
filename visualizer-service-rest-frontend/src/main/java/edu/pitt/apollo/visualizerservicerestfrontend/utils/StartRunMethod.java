/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.visualizerservicerestfrontend.utils;

import edu.pitt.apollo.VisualizerServiceImpl;
import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.exception.*;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.SerializerFactory;
import java.math.BigInteger;

import edu.pitt.apollo.utils.AuthorizationUtility;
import edu.pitt.apollo.utils.UnsupportedAuthorizationTypeException;
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
		serializer = SerializerFactory.getSerializer(serializationFormat);

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
