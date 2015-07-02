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
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nem41
 */
public class RestServiceUtils {

	RestTemplate template = new RestTemplate();
	HttpHeaders headers = new HttpHeaders();

	public RestServiceUtils() {
		// add the neccesary converts to the template
		List<HttpMessageConverter<?>> converters = new ArrayList<>();
		converters.add(new RequestHttpMessageConverter());
		converters.add(new ResponseHttpMessageConverter());
		template.setMessageConverters(converters);

		// set the headers
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		headers.setContentType(MediaType.APPLICATION_XML);
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

		if (meta.isIsBodySerialized()) {
			SerializationFormat serializationFormat = meta.getResponseBodySerializationInformation().getFormat();
			try {
				Deserializer deserializer = DeserializerFactory.getDeserializer(serializationFormat);
				T object = deserializer.getObjectFromMessage(response.getResponseBody().get(0), clazz);
				return object;
			} catch (DeserializationException | UnsupportedSerializationFormatException ex) {
				throw new RestServiceException(ex.getMessage());
			}
		} else {
			return (T) response.getResponseBody().get(0);
		}
	}

	public <T> List<T> getObjectsFromResponseBody(Response response, Class<T> clazz) throws RestServiceException {
		ResponseMeta meta = response.getResponseMeta();
		if (meta.isIsBodySerialized()) {
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
		} else {
			List<T> list = new ArrayList<>();
			for (String s : response.getResponseBody()) {
				list.add((T) s);
			}
			return list;
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
			request.setRequestMeta(requestMeta);
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
		HttpEntity<Request> entity = new HttpEntity<>(headers);
		ResponseEntity<Response> responseEntity = template.exchange(uri, HttpMethod.GET, entity, Response.class);
		checkResponse(responseEntity.getBody());
	}

	public void makePostRequestAndCheckResponse(String uri, Object object) throws RestServiceException {
		HttpEntity<Request> entity = new HttpEntity<>(headers);
		ResponseEntity<Response> responseEntity = template.exchange(uri, HttpMethod.POST, entity, Response.class);
		checkResponse(responseEntity.getBody());
	}

	public <T> T makeGetRequestCheckResponseAndGetObject(String uri, Class<T> clazz) throws RestServiceException {
		HttpEntity<Request> entity = new HttpEntity<>(headers);
		ResponseEntity<Response> responseEntity = template.exchange(uri, HttpMethod.GET, entity, Response.class);

		return checkResponseAndGetObject(responseEntity.getBody(), clazz);
	}

	public <T> List<T> makeGetRequestCheckResponseAndGetObjects(String uri, Class<T> clazz) throws RestServiceException {
		HttpEntity<Request> entity = new HttpEntity<>(headers);
		ResponseEntity<Response> responseEntity = template.exchange(uri, HttpMethod.GET, entity, Response.class);

		return checkResponseAndGetObjects(responseEntity.getBody(), clazz);
	}

	public <T> T makePostRequestCheckResponseAndGetObject(String uri, Object object, Class<T> clazz) throws RestServiceException {

		Request request = getRequestObjectWithSerializedBody(object);
		HttpEntity<Request> entity = new HttpEntity<>(request, headers);
		ResponseEntity<Response> responseEntity = template.exchange(uri, HttpMethod.POST, entity, Response.class);

		return checkResponseAndGetObject(responseEntity.getBody(), clazz);
	}

	public <T> List<T> makePostRequestCheckResponseAndGetObjects(String uri, Object object, Class<T> clazz) throws RestServiceException {
		Request request = getRequestObjectWithSerializedBody(object);
		HttpEntity<Request> entity = new HttpEntity<>(request, headers);
		ResponseEntity<Response> responseEntity = template.exchange(uri, HttpMethod.POST, entity, Response.class);
		return checkResponseAndGetObjects(responseEntity.getBody(), clazz);
	}

	public void makeDeleteRequestAndCheckResponse(String uri) throws RestServiceException {

		ResponseEntity<Response> responseEntity = template.exchange(null, HttpMethod.DELETE, HttpEntity.EMPTY, Response.class);
		Response response = responseEntity.getBody();
		checkResponseCode(response);
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
