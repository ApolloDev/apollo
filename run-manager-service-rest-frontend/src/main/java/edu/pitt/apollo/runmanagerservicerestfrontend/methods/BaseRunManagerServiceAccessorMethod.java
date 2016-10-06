/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.runmanagerservicerestfrontend.methods;

import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.runmanagerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.runmanagerservice.RunManagerServiceImpl;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.SerializerFactory;
import edu.pitt.apollo.utils.AuthorizationUtility;
import edu.pitt.apollo.utils.UnsupportedAuthorizationTypeException;

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
		
		serializer = SerializerFactory.getSerializer(serializationFormat);

		impl = new RunManagerServiceImpl();
	}

}
