/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.brokerservicerestfrontend.methods;

import edu.pitt.apollo.brokerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.isg.objectserializer.exceptions.DeserializationException;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import edu.pitt.apollo.services_common.v4_0_1.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0_1.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import java.math.BigInteger;

import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */


public class UploadFileMethod extends BaseBrokerServiceAccessorMethod {

	public UploadFileMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, authorizationHeader);
	}

	public String uploadFile(BigInteger runId, String urlOfFile, String filename, ContentDataFormatEnum contentDataFormatEnum,
			ContentDataTypeEnum contentDataTypeEnum) throws UnsupportedSerializationFormatException, DeserializationException, SerializationException {

		try {
			
			impl.uploadFile(runId, urlOfFile, filename, contentDataFormatEnum, contentDataTypeEnum, authentication);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE).setIsBodySerialized(false);

		} catch (FilestoreException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}
}
