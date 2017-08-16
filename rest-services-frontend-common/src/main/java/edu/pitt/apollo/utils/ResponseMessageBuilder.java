/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.utils;

import edu.pitt.apollo.services_common.v4_0_2.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0_2.Response;
import edu.pitt.apollo.services_common.v4_0_2.ResponseMeta;
import org.springframework.http.HttpStatus;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nem41
 */
public class ResponseMessageBuilder {

	public static final String DEFAULT_SUCCESS_MESSAGE = "The request was successful";
	private final Response response;

	public ResponseMessageBuilder() {

		response = new Response();
		response.setResponseMeta(new ResponseMeta());
	}

	public Response getResponse() {
		return response;
	}
	
	public ResponseMessageBuilder setStatus(HttpStatus status, String message) {
		response.getResponseMeta().setStatus(new BigInteger(Integer.toString(status.value())));
		response.getResponseMeta().setStatusMessage(message);
		return this;
	}

	public ResponseMessageBuilder setSource(String source) {
		response.getResponseMeta().setSource(source);
		return this;
	}

	public ResponseMessageBuilder setResponseBodySerializationInformation(ObjectSerializationInformation objectSerializationInformation) {
		response.getResponseMeta().setResponseBodySerializationInformation(objectSerializationInformation);
		return this;
	}

	public ResponseMessageBuilder addContentToBody(String serializedObject) {
		List<String> list = new ArrayList<>();
		list.add(serializedObject);
		response.getResponseBody().add(serializedObject);
		return this;
	}

	public ResponseMessageBuilder addContentToBody(List<String> serializedObjects) {
		response.getResponseBody().addAll(serializedObjects);
		return this;
	}
	
	public ResponseMessageBuilder setIsBodySerialized(boolean isBodySerialized) {
		response.getResponseMeta().setIsBodySerialized(isBodySerialized);
		return this;
	}

}
