/*
 * Copyright 2015 nem41.
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
package edu.pitt.apollo.restserviceconnectorcommon;

import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.restserviceconnectorcommon.exception.RestServiceException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.Response;
import edu.pitt.apollo.services_common.v3_0_0.ResponseMeta;
import edu.pitt.apollo.services_common.v3_0_0.SerializationFormat;
import edu.pitt.apollo.utilities.Deserializer;
import edu.pitt.apollo.utilities.DeserializerFactory;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class RestServiceUtils {

	public static String getUsernameAndPasswordQueryParams(Authentication authentication) {
		return "username=" + authentication.getRequesterId() + "&password=" + authentication.getRequesterPassword();
	}

	public static void checkResponseCode(Response response) throws RestServiceException {
		ResponseMeta meta = response.getResponseMeta();
		BigInteger status = meta.getStatus();
		if (status.intValue() != HttpStatus.OK.value()) {
			// there was an error
			throw new RestServiceException("The response code returned from the service was "
					+ status + ". The message is: " + meta.getStatusMessage());
		}
	}

	public static <T> T getObjectFromResponseBody(Response response, Class<T> clazz) throws RestServiceException {
		ResponseMeta meta = response.getResponseMeta();
		SerializationFormat serializationFormat = meta.getResponseBodySerializationInformation().getFormat();
		try {
			Deserializer deserializer = DeserializerFactory.getDeserializer(serializationFormat);
			T object = deserializer.getObjectFromMessage(response.getResponseBody().get(0), clazz);
			return object;
		} catch (DeserializationException | UnsupportedSerializationFormatException ex) {
			throw new RestServiceException(ex.getMessage());
		}
	}

	public static <T> List<T> getObjectsFromResponseBody(Response response, Class<T> clazz) throws RestServiceException {
		ResponseMeta meta = response.getResponseMeta();
		SerializationFormat serializationFormat = meta.getResponseBodySerializationInformation().getFormat();
		try {
			Deserializer deserializer = DeserializerFactory.getDeserializer(serializationFormat);
			List<T> deserializedObjects = new ArrayList<>();

			List<String> serializedObjects = response.getResponseBody();
			for (String serializedObject : serializedObjects) {
				T object = deserializer.getObjectFromMessage(serializedObject, clazz);
				deserializedObjects.add(object);
			}

			return deserializedObjects;
		} catch (DeserializationException | UnsupportedSerializationFormatException ex) {
			throw new RestServiceException(ex.getMessage());
		}
	}

}
