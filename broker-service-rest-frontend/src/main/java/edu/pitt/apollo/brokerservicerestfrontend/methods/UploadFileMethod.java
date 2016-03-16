/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.brokerservicerestfrontend.methods;

import edu.pitt.apollo.brokerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.utilities.XMLDeserializer;
import java.math.BigInteger;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */


public class UploadFileMethod extends BaseBrokerServiceAccessorMethod {

	public UploadFileMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat);
	}

	public String uploadFile(BigInteger runId, String urlOfFile, String messageBody) throws UnsupportedSerializationFormatException, DeserializationException, SerializationException {

		try {
			FileIdentification fileIdentification = new XMLDeserializer().getObjectFromMessage(messageBody, FileIdentification.class);

			impl.uploadFile(runId, urlOfFile, fileIdentification, authentication);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE).setIsBodySerialized(false);

		} catch (FilestoreException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}
}
