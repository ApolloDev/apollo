/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.brokerservicerestfrontend.methods;

import edu.pitt.apollo.BrokerServiceImpl;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.brokerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.SerializerFactory;
import edu.pitt.apollo.utilities.AuthorizationUtility;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;

/**
 *
 * @author nem41
 */
public abstract class BaseBrokerServiceAccessorMethod {

	protected final Authentication authentication;
	protected final Serializer serializer;
	protected final SerializationFormat serializationFormat;
	protected final BrokerServiceImpl impl;
	protected final ResponseMessageBuilder responseBuilder;

	public BaseBrokerServiceAccessorMethod(SerializationFormat serializationFormat, String authorizationHesder) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {

		authentication = AuthorizationUtility.createAuthenticationFromAuthorizationHeader(authorizationHesder);

		responseBuilder = new ResponseMessageBuilder();
		this.serializationFormat = serializationFormat;
		serializer = SerializerFactory.getSerializer(serializationFormat);

		impl = new BrokerServiceImpl();

	}

}
