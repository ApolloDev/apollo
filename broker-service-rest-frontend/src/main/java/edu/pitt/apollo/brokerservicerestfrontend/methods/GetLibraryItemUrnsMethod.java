package edu.pitt.apollo.brokerservicerestfrontend.methods;

import edu.pitt.apollo.brokerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.library_service_types.v4_0_2.GetLibraryItemURNsResult;
import edu.pitt.apollo.services_common.v4_0_2.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0_2.SerializationFormat;
import edu.pitt.isg.objectserializer.Serializer;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

/**
 * Created by jdl50 on 8/13/15.
 */
public class GetLibraryItemUrnsMethod extends BaseBrokerServiceAccessorMethod {

	public GetLibraryItemUrnsMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, authorizationHeader);
	}

	public String getLibraryItemUrns(String itemType) throws SerializationException {
		try {
			GetLibraryItemURNsResult result = impl.getLibraryItemURNs(itemType, authentication);
			ObjectSerializationInformation serializationInformation = new ObjectSerializationInformation();
            serializationInformation.setClassNameSpace(Serializer.APOLLO_NAMESPACE);
            serializationInformation.setClassName(GetLibraryItemURNsResult.class.getSimpleName());
            serializationInformation.setFormat(SerializationFormat.XML);

            String serializedObject = serializer.serializeObject(result);

            responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
                    .setResponseBodySerializationInformation(serializationInformation).addContentToBody(serializedObject).setIsBodySerialized(true);

			
			return serializer.serializeObject(responseBuilder.getResponse());
		} catch (LibraryServiceException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getClass().getName() + ": " + ex.getMessage());
			try {
				return serializer.serializeObject(responseBuilder.getResponse());
			} catch (SerializationException e1) {
				return "Error: " + e1.getClass().getName() + ": " + e1.getMessage();
			}
		}
	}
}