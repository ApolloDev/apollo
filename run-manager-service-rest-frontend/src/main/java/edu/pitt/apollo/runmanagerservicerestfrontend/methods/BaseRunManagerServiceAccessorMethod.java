/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.runmanagerservicerestfrontend.methods;


import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.runmanagerservice.RunManagerServiceImpl;
import edu.pitt.apollo.runmanagerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.SerializationFormat;
import edu.pitt.apollo.utilities.ApolloClassList;
import edu.pitt.apollo.utilities.AuthorizationUtility;
import edu.pitt.isg.objectserializer.Serializer;
import edu.pitt.isg.objectserializer.SerializerFactory;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;

import java.util.Arrays;

/**
 *
 * @author nem41
 */
public abstract class BaseRunManagerServiceAccessorMethod {

	protected final Authentication authentication;
	protected final Serializer serializer;
	protected final RunManagerServiceImpl impl;
	protected final ResponseMessageBuilder responseBuilder;

	public BaseRunManagerServiceAccessorMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {

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
		}

		impl = new RunManagerServiceImpl();
	}

}
