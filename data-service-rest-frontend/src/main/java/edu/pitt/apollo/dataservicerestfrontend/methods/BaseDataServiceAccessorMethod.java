/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.dataservicerestfrontend.methods;

import edu.pitt.apollo.DataServiceImpl;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.dataservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v3_1_0.Authentication;
import edu.pitt.apollo.services_common.v3_1_0.SerializationFormat;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.SerializerFactory;

/**
 *
 * @author nem41
 */
public abstract class BaseDataServiceAccessorMethod {

	protected final Authentication authentication;
	protected final Serializer serializer;
	protected final DataServiceImpl impl;
	protected final ResponseMessageBuilder responseBuilder;

	public BaseDataServiceAccessorMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {

		authentication = new Authentication();
		authentication.setRequesterId(username);
		authentication.setRequesterPassword(password);

		responseBuilder = new ResponseMessageBuilder();
		
		serializer = SerializerFactory.getSerializer(serializationFormat);

		impl = new DataServiceImpl();

	}

}
