/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.brokerservicerestfrontend.methods;

import edu.pitt.apollo.brokerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.data_service_types.v4_0.ContentIdAndDescription;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.services_common.v4_0.FileAndURLDescription;
import edu.pitt.apollo.services_common.v4_0.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.utilities.Serializer;
import org.springframework.http.HttpStatus;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nem41
 */
public class GetListOfURLsForRunMethod extends BaseBrokerServiceAccessorMethod {

	public GetListOfURLsForRunMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat);
	}
	
	public String getListOfURLsForRunMethod(BigInteger runId) throws UnsupportedSerializationFormatException, SerializationException {
	
		try {
			Map<BigInteger, FileAndURLDescription> files = impl.getListOfURLsForRunId(runId, authentication);

			List<String> serializedContents = new ArrayList<>();

			for (BigInteger id : files.keySet()) {
				ContentIdAndDescription idAndDescription = new ContentIdAndDescription();
				idAndDescription.setContentId(id);
				idAndDescription.setContentDescription(files.get(id));

				String serializedObject = serializer.serializeObject(idAndDescription);
				serializedContents.add(serializedObject);
			}

			ObjectSerializationInformation objectSerializationInformation = new ObjectSerializationInformation();
			objectSerializationInformation.setClassNameSpace(Serializer.APOLLO_NAMESPACE);
			objectSerializationInformation.setClassName(ContentIdAndDescription.class.getSimpleName());
			objectSerializationInformation.setFormat(SerializationFormat.XML);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
					.setResponseBodySerializationInformation(objectSerializationInformation).addContentToBody(serializedContents).setIsBodySerialized(true);

		} catch (DataServiceException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}
	
}
