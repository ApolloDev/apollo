/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.runmanagerservicerestfrontend.methods;

import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.runmanagerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v4_0.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import edu.pitt.apollo.utilities.Serializer;
import java.math.BigInteger;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class GetSoftwareIdentificationForRunMethod_1 extends BaseRunManagerServiceAccessorMethod {

	public GetSoftwareIdentificationForRunMethod_1(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat);
	}

	public String getSoftwareIdentificationForRun(BigInteger runId) throws UnsupportedSerializationFormatException, SerializationException {

		try {
			SoftwareIdentification softwareId;
			softwareId = impl.getSoftwareIdentificationForRun(runId, authentication);

			ObjectSerializationInformation serializationInformation = new ObjectSerializationInformation();
			serializationInformation.setClassNameSpace(Serializer.APOLLO_NAMESPACE);
			serializationInformation.setClassName(softwareId.getClass().getSimpleName());
			serializationInformation.setFormat(SerializationFormat.XML);

			String serializedObject = serializer.serializeObject(softwareId);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
					.setResponseBodySerializationInformation(serializationInformation).addContentToBody(serializedObject).setIsBodySerialized(true);

		} catch (RunManagementException | SerializationException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());

	}

}