/*
 * Copyright 2016 nem41.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.pitt.apollo.filestoreservicerestfrontend.methods;

import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.filestoreservice.FileStoreService;
import edu.pitt.apollo.filestoreservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.SerializerFactory;
import edu.pitt.apollo.utils.AuthorizationUtility;

/**
 *
 * @author nem41
 */
public abstract class BaseFileStoreMethod {

	protected final Serializer serializer;
	protected final FileStoreService fileStoreService;
	protected final ResponseMessageBuilder responseBuilder;
    protected final Authentication authentication;

	public BaseFileStoreMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException {

		responseBuilder = new ResponseMessageBuilder();

		serializer = SerializerFactory.getSerializer(serializationFormat);

		fileStoreService = new FileStoreService();
        authentication = AuthorizationUtility.createAuthenticationFromAuthorizationHeader(authorizationHeader);
	}

}
