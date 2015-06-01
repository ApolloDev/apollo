/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.restservice.rest.utils;

import edu.pitt.apollo.services_common.v3_0_0.Response;
import edu.pitt.apollo.services_common.v3_0_0.ResponseMeta;
import edu.pitt.apollo.services_common.v3_0_0.SerializationConfiguration;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nem41
 */
public class ResponseMessageBuilder {
	
	public static final String HTTP_SUCCESSFUL_RESPONSE_CODE = "200";
	public static final String HTTP_SERVER_ERROR_RESPONSE_CODE = "500";
	public static final String HTTP_BAD_REQUEST_RESPONSE_CODE = "400";
	public static final String SUCCESSFUL_RESPONSE_MESSAGE = "The request was successful";

	public static Response buildSuccessfulResponse() {

		Response response = new Response();

		ResponseMeta meta = new ResponseMeta();
		meta.setNumberOfReturnedResults(BigInteger.ZERO);
		meta.setStatus(new BigInteger(HTTP_SUCCESSFUL_RESPONSE_CODE));
		meta.setStatusMessage(SUCCESSFUL_RESPONSE_MESSAGE);

		response.setResponseMeta(meta);
		return response;
	}

	public static Response buildFailedResponseForServerError(String message) {
		return buildFailedResponse(message, HTTP_SERVER_ERROR_RESPONSE_CODE);
	}
	
	public static Response buildFailedResponseForBadRequest(String message) {
		return buildFailedResponse(message, HTTP_BAD_REQUEST_RESPONSE_CODE);
	}
	
	public static Response buildFailedResponse(String message, String responseCode) {
		Response response = new Response();

		ResponseMeta meta = new ResponseMeta();
		meta.setNumberOfReturnedResults(BigInteger.ZERO);
		meta.setStatus(new BigInteger(HTTP_SERVER_ERROR_RESPONSE_CODE));
		meta.setStatusMessage("There was a server error. The error was: " + message);

		response.setResponseMeta(meta);
		return response;
	}
	
	public static Response buildResponse(String httpResponseCode, String statusMessage, 
			List<String> serializedReturnObjects, SerializationConfiguration serializationConfiguration) {
		Response response = new Response();

		ResponseMeta meta = new ResponseMeta();
		meta.setNumberOfReturnedResults(new BigInteger(Integer.toString(serializedReturnObjects.size())));
		meta.setStatus(new BigInteger(httpResponseCode));
		meta.setStatusMessage(statusMessage);
		meta.setSerializationConfiguration(serializationConfiguration);

		response.setResponseMeta(meta);
		response.getSerializedObject().addAll(serializedReturnObjects);
		
		return response;
	}
	
	public static Response buildResponseForSingleObject(String httpResponseCode, String statusMessage, 
			String serializedObject, SerializationConfiguration serializationConfiguration) {
		
		List<String> list = new ArrayList<>();
		list.add(serializedObject);
		return buildResponse(httpResponseCode, statusMessage, list, serializationConfiguration);
	}

}
