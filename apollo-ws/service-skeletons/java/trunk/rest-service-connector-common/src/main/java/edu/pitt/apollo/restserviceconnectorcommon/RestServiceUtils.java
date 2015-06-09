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
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.restserviceconnectorcommon.exception.RestServiceException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v3_0_0.Request;
import edu.pitt.apollo.services_common.v3_0_0.RequestMeta;
import edu.pitt.apollo.services_common.v3_0_0.Response;
import edu.pitt.apollo.services_common.v3_0_0.ResponseMeta;
import edu.pitt.apollo.services_common.v3_0_0.SerializationFormat;
import edu.pitt.apollo.utilities.Deserializer;
import edu.pitt.apollo.utilities.DeserializerFactory;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.SerializerFactory;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nem41
 */
public class RestServiceUtils {

	RestTemplate template = new RestTemplate();

	public RestServiceUtils() {

	}

	public static String getUsernameAndPasswordQueryParams(Authentication authentication) {
		return "username=" + authentication.getRequesterId() + "&password=" + authentication.getRequesterPassword();
	}

	public void checkResponseCode(Response response) throws RestServiceException {
		ResponseMeta meta = response.getResponseMeta();
		BigInteger status = meta.getStatus();
		if (status.intValue() != HttpStatus.OK.value()) {
			// there was an error
			throw new RestServiceException("The response code returned from the service was "
					+ status + ". The message is: " + meta.getStatusMessage());
		}
	}

	public <T> T getObjectFromResponseBody(Response response, Class<T> clazz) throws RestServiceException {
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

	public <T> List<T> getObjectsFromResponseBody(Response response, Class<T> clazz) throws RestServiceException {
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

	public Request getRequestObjectWithSerializedBody(Object bodyObject) throws RestServiceException {
		Request request = new Request();
		RequestMeta requestMeta = new RequestMeta();
		requestMeta.setIsBodySerialized(true);

		try {
			Serializer serializer = SerializerFactory.getSerializer(SerializationFormat.XML);
			ObjectSerializationInformation objectSerializationInformation = serializer.getObjectSerializationInformation(bodyObject);
			requestMeta.setRequestBodySerializationInformation(objectSerializationInformation);

			String serializedObject = serializer.serializeObject(bodyObject);
			request.setRequestBody(serializedObject);
		} catch (SerializationException | UnsupportedSerializationFormatException ex) {
			throw new RestServiceException(ex.getMessage());
		}

		return request;
	}

	// helper methods
	public void checkResponse(Response response) throws RestServiceException {
		checkResponseCode(response);
	}

	public void makeGetRequestAndCheckResponse(String uri) throws RestServiceException {
		Response response = template.getForObject(uri, Response.class);
		checkResponse(response);
	}

	public void makePostRequestAndCheckResponse(String uri, Object object) throws RestServiceException {
		Response response = template.postForObject(uri, object, Response.class);
		checkResponse(response);
	}

	public <T> T makeGetRequestCheckResponseAndGetObject(String uri, Class<T> clazz) throws RestServiceException {
		Response response = template.getForObject(uri, Response.class);
		return checkResponseAndGetObject(response, clazz);
	}

	public <T> List<T> makeGetRequestCheckResponseAndGetObjects(String uri, Class<T> clazz) throws RestServiceException {
		Response response = template.getForObject(uri, Response.class);
		return checkResponseAndGetObjects(response, clazz);
	}

	public <T> T makePostRequestCheckResponseAndGetObject(String uri, Object object, Class<T> clazz) throws RestServiceException {
		Request request = getRequestObjectWithSerializedBody(object);
		Response response = template.postForObject(uri, request, Response.class);
		return checkResponseAndGetObject(response, clazz);
	}

	public <T> List<T> makePostRequestCheckResponseAndGetObjects(String uri, Object object, Class<T> clazz) throws RestServiceException {
		Request request = getRequestObjectWithSerializedBody(object);
		Response response = template.postForObject(uri, request, Response.class);
		return checkResponseAndGetObjects(response, clazz);
	}

	public <T> T checkResponseAndGetObject(Response response, Class<T> clazz) throws RestServiceException {
		checkResponseCode(response);
		return getObjectFromResponseBody(response, clazz);
	}

	public <T> List<T> checkResponseAndGetObjects(Response response, Class<T> clazz) throws RestServiceException {
		checkResponseCode(response);
		return getObjectsFromResponseBody(response, clazz);
	}

}
